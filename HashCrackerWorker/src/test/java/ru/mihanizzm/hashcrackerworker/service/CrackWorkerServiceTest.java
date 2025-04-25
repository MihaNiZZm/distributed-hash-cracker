package ru.mihanizzm.hashcrackerworker.service;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.mihanizzm.hashcrackerworker.msg.WorkerTaskRequest;
import ru.mihanizzm.hashcrackerworker.msg.WorkerTaskResult;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CrackWorkerServiceTest {
    @Test
    void testCrackTaskToFindWordInItsPart() {
        WorkerTaskRequest req = WorkerTaskRequest.builder()
                .alphabet("abcdefghijklmnopqrstuvwxyz0123456789")
                .hash("900150983cd24fb0d6963f7d28e17f72")
                .maxLength(3)
                .partCount(2)
                .partNumber(0)
                .requestId("id")
                .build();
        RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);

        CrackWorkerService service = new CrackWorkerService(rabbitTemplate);
        WorkerTaskResult expected = WorkerTaskResult.builder()
                .requestId(req.getRequestId())
                .partNumber(req.getPartNumber())
                .results(List.of("abc"))
                .build();

        service.processTask(req);
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), eq(expected));
    }

    @Test
    void testCrackTaskNotToFindWordInItsPart() {
        WorkerTaskRequest req = WorkerTaskRequest.builder()
                .alphabet("abcdefghijklmnopqrstuvwxyz0123456789")
                .hash("900150983cd24fb0d6963f7d28e17f72")
                .maxLength(3)
                .partCount(2)
                .partNumber(1)
                .requestId("id")
                .build();
        RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);

        CrackWorkerService service = new CrackWorkerService(rabbitTemplate);
        WorkerTaskResult expected = WorkerTaskResult.builder()
                .requestId(req.getRequestId())
                .partNumber(req.getPartNumber())
                .results(List.of())
                .build();

        service.processTask(req);
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), eq(expected));
    }
}