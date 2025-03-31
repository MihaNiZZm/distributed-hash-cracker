package ru.mihanizzm.hashcrackermanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mihanizzm.hashcrackermanager.dto.HashCrackResult;
import ru.mihanizzm.hashcrackermanager.dto.HashCrackTask;
import ru.mihanizzm.hashcrackermanager.model.CrackRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CrackService {
    private final RequestStorage requestStorage;
    private final RestTemplate restTemplate;

    @Value("${worker.count:1}")
    private int workerCount;

    @Value("${request.timeout.minutes:10}")
    private int requestTimeout;

    public String processRequest(String hash, int maxLength) {
        String requestId = UUID.randomUUID().toString();
        CrackRequest request = new CrackRequest(requestId, hash, maxLength, workerCount);
        requestStorage.addRequest(request);

        IntStream.range(0, workerCount).forEach(partNumber -> {
            HashCrackTask task = new HashCrackTask();
            task.setRequestId(requestId);
            task.setHash(hash);
            task.setLength(maxLength);
            task.setPartCount(workerCount);
            task.setPartNumber(partNumber);

            sendTaskToWorker(task);
        });

        return requestId;
    }

    public void updateRequest(HashCrackResult result) {
        CrackRequest request = requestStorage.getRequest(result.getRequestId());
        if (request != null) {
            request.getResults().addAll(result.getData());
            request.getReceivedParts().add(result.getPartNumber());

            if (request.getReceivedParts().size() == workerCount) {
                request.setStatus(CrackRequest.Status.READY);
            }
        }
    }

    private void sendTaskToWorker(HashCrackTask task) {
        String workerUrl = "http://worker/internal/api/worker/hash/crack/task";
        restTemplate.postForEntity(workerUrl, task, Void.class);
    }

    @Scheduled(fixedRate = 60_000)
    public void checkTimeouts() {
        requestStorage.getAllRequests().forEach(request -> {
            if (Duration.between(request.getCreated(), Instant.now()).toMinutes() > requestTimeout) {
                request.setStatus(CrackRequest.Status.ERROR);
            }
        });
    }

    public CrackRequest getRequest(String requestId) {
        return requestStorage.getRequest(requestId);
    }
}
