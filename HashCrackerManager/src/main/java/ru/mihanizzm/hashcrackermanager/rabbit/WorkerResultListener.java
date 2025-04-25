package ru.mihanizzm.hashcrackermanager.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.mihanizzm.hashcrackermanager.config.RabbitMQConfig;
import ru.mihanizzm.hashcrackermanager.msg.WorkerTaskResult;
import ru.mihanizzm.hashcrackermanager.service.CrackService;

@Component
@RequiredArgsConstructor
public class WorkerResultListener {

    private final CrackService crackService;

    // Получать сообщения из очереди результатов
    @RabbitListener(queues = RabbitMQConfig.MANAGER_RESULTS_QUEUE)
    public void receiveResult(WorkerTaskResult result) {
        crackService.handleWorkerResult(result);
    }
}
