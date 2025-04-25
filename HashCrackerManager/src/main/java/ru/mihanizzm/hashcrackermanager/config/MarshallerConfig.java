package ru.mihanizzm.hashcrackermanager.config;

import org.springframework.amqp.support.converter.MarshallingMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class MarshallerConfig {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("ru.mihanizzm.hashcrackermanager.msg");
        return marshaller;
    }

    @Bean
    public MarshallingMessageConverter amqpMessageConverter(Jaxb2Marshaller marshaller) {
        return new MarshallingMessageConverter(marshaller, marshaller);
    }
}
