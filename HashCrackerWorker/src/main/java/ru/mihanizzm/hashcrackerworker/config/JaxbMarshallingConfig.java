package ru.mihanizzm.hashcrackerworker.config;

import org.springframework.amqp.support.converter.MarshallingMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class JaxbMarshallingConfig {
    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("ru.mihanizzm.hashcrackerworker.msg");
        return marshaller;
    }

    @Bean
    public MarshallingMessageConverter amqpMessageConverter(Jaxb2Marshaller marshaller) {
        return new MarshallingMessageConverter(marshaller, marshaller);
    }
}