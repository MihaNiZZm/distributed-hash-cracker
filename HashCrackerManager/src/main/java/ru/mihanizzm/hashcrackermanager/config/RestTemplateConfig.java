package ru.mihanizzm.hashcrackermanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;
import ru.mihanizzm.hashcrackermanager.dto.HashCrackResult;
import ru.mihanizzm.hashcrackermanager.dto.HashCrackTask;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(Jaxb2Marshaller marshaller) {
        RestTemplate restTemplate = new RestTemplate();
        MarshallingHttpMessageConverter converter = new MarshallingHttpMessageConverter(marshaller, marshaller);
        restTemplate.getMessageConverters().addFirst(converter);
        return restTemplate;
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(HashCrackTask.class, HashCrackResult.class);
        return marshaller;
    }
}
