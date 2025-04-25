package ru.mihanizzm.hashcrackerworker.rabbit;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.mihanizzm.hashcrackerworker.config.RabbitMQConfig;
import ru.mihanizzm.hashcrackerworker.msg.WorkerTaskRequest;
import ru.mihanizzm.hashcrackerworker.service.CrackWorkerService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class WorkerTaskListener {

    private final CrackWorkerService workerService;

    @RabbitListener(queues = RabbitMQConfig.WORKER_TASKS_QUEUE, containerFactory = "containerFactory")
    public void onTask(WorkerTaskRequest request, Channel channel, Message message) {
        try {
            workerService.processTask(request);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
