package ru.mihanizzm.hashcrackermanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mihanizzm.hashcrackermanager.config.RabbitMQConfig;
import ru.mihanizzm.hashcrackermanager.dto.CrackRequest;
import ru.mihanizzm.hashcrackermanager.dto.CrackResponse;
import ru.mihanizzm.hashcrackermanager.dto.CrackStatusResponse;
import ru.mihanizzm.hashcrackermanager.model.CrackStatus;
import ru.mihanizzm.hashcrackermanager.model.CrackSubTask;
import ru.mihanizzm.hashcrackermanager.model.CrackTask;
import ru.mihanizzm.hashcrackermanager.msg.WorkerTaskRequest;
import ru.mihanizzm.hashcrackermanager.msg.WorkerTaskResult;
import ru.mihanizzm.hashcrackermanager.repository.CrackTaskRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CrackService {
    private final CrackTaskRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final long expirationMillis = 60_000;

    private final String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";
    private final int workerCount = 2;

    @Transactional
    public CrackResponse startCrack(CrackRequest req) {
        String requestId = UUID.randomUUID().toString();

        List<CrackSubTask> subTasks = new ArrayList<>();
        for (int i = 0; i < workerCount; i++) {
            subTasks.add(CrackSubTask.builder()
                    .partNumber(i)
                    .sentToQueue(false)
                    .completed(false)
                    .build());
        }

        CrackTask task = CrackTask.builder()
                .requestId(requestId)
                .hash(req.getHash())
                .maxLength(req.getMaxLength())
                .alphabet(alphabet)
                .workerCount(workerCount)
                .subTasks(subTasks)
                .results(new HashSet<>())
                .status(CrackStatus.IN_PROGRESS)
                .createdAt(System.currentTimeMillis())
                .build();

        repository.save(task);

        for (CrackSubTask chunk : subTasks) {
            sendSubTaskToQueue(task, chunk);
        }
        repository.save(task);

        return new CrackResponse(requestId);
    }

    @Transactional
    public void handleWorkerResult(WorkerTaskResult result) {
        CrackTask task = repository.findById(result.getRequestId()).orElse(null);
        if (task == null) {
            return;
        }

        CrackSubTask chunk = task.getSubTasks().stream()
                .filter(st -> st.getPartNumber() == result.getPartNumber())
                .findFirst()
                .orElse(null);
        if (chunk == null || chunk.isCompleted()) {
            return;
        }

        if (result.getResults() != null) {
            chunk.getResults().addAll(result.getResults());
        }

        chunk.setCompleted(true);
        task.getResults().addAll(chunk.getResults());

        if (task.getSubTasks().stream().allMatch(CrackSubTask::isCompleted)) {
            task.setStatus(CrackStatus.READY);
        }
        repository.save(task);
    }

    public CrackStatusResponse getStatus(String requestId) {
        CrackTask task = repository.findById(requestId).orElse(null);
        if (task == null) {
            return new CrackStatusResponse("ERROR", null);
        }

        if (task.getStatus() == CrackStatus.READY) {
            return new CrackStatusResponse("READY", new ArrayList<>(task.getResults()));
        }

        if (task.getStatus() == CrackStatus.IN_PROGRESS) {
            return new CrackStatusResponse("IN_PROGRESS", null);
        }

        return new CrackStatusResponse("ERROR", null);
    }

    @Scheduled(fixedDelay = 10_000)
    public void resendNotSentChunks() {
        List<CrackTask> tasks = repository.findAllByStatus(CrackStatus.IN_PROGRESS);
        for (CrackTask task : tasks) {
            for (CrackSubTask chunk : task.getSubTasks()) {
                if (!chunk.isSentToQueue() && !chunk.isCompleted()) {
                    sendSubTaskToQueue(task, chunk);
                }
            }
            repository.save(task);
        }
    }

    @Scheduled(fixedDelay = 10_000)
    public void markExpiredTasksAsError() {
        long now = System.currentTimeMillis();
        List<CrackTask> tasks = repository.findAllByStatus(CrackStatus.IN_PROGRESS);
        for (CrackTask t : tasks) {
            if (now - t.getCreatedAt() > expirationMillis) {
                t.setStatus(CrackStatus.ERROR);
                repository.save(t);
            }
        }
    }

    private void sendSubTaskToQueue(CrackTask task, CrackSubTask chunk) {
        WorkerTaskRequest workerTaskReq = WorkerTaskRequest.builder()
                .requestId(task.getRequestId())
                .hash(task.getHash())
                .alphabet(task.getAlphabet())
                .maxLength(task.getMaxLength())
                .partNumber(chunk.getPartNumber())
                .partCount(task.getWorkerCount())
                .build();
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.WORKER_TASKS_EXCHANGE,
                    RabbitMQConfig.WORKER_TASKS_ROUTING_KEY,
                    workerTaskReq
            );
            chunk.setSentToQueue(true);
        } catch (Exception e) {
            chunk.setSentToQueue(false);
        }
    }
}
