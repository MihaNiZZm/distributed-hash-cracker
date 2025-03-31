package ru.mihanizzm.hashcrackerworker.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;
import ru.mihanizzm.hashcrackerworker.dto.HashCrackResult;
import ru.mihanizzm.hashcrackerworker.dto.HashCrackTask;

@Configuration
public class RestTemplateConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(HashCrackTask.class, HashCrackResult.class);
        return marshaller;
    }

    @Bean
    public RestTemplate restTemplate(Jaxb2Marshaller marshaller, RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        restTemplate.getMessageConverters().addFirst(new MarshallingHttpMessageConverter(marshaller, marshaller));
        return restTemplate;
    }
}
