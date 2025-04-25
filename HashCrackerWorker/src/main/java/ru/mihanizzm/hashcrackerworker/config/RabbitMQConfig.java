package ru.mihanizzm.hashcrackerworker.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String WORKER_TASKS_QUEUE = "worker.tasks";
    public static final String WORKER_TASKS_EXCHANGE = "worker.tasks.exchange";
    public static final String WORKER_TASKS_ROUTING_KEY = "worker.tasks";

    public static final String MANAGER_RESULTS_QUEUE = "manager.results";
    public static final String MANAGER_RESULTS_EXCHANGE = "manager.results.exchange";
    public static final String MANAGER_RESULTS_ROUTING_KEY = "manager.results";

    @Bean
    public Queue workerTasksQueue() {
        return QueueBuilder.durable(WORKER_TASKS_QUEUE).build();
    }

    @Bean
    public DirectExchange workerTasksExchange() {
        return new DirectExchange(WORKER_TASKS_EXCHANGE);
    }

    @Bean
    public Binding workerTasksBinding() {
        return BindingBuilder
                .bind(workerTasksQueue())
                .to(workerTasksExchange())
                .with(WORKER_TASKS_ROUTING_KEY);
    }

    @Bean
    public Queue managerResultsQueue() {
        return QueueBuilder.durable(MANAGER_RESULTS_QUEUE).build();
    }

    @Bean
    public DirectExchange managerResultsExchange() {
        return new DirectExchange(MANAGER_RESULTS_EXCHANGE);
    }

    @Bean
    public Binding managerResultsBinding() {
        return BindingBuilder
                .bind(managerResultsQueue())
                .to(managerResultsExchange())
                .with(MANAGER_RESULTS_ROUTING_KEY);
    }
}
