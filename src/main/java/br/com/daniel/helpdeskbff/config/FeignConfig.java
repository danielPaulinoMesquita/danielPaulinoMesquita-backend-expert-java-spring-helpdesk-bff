package br.com.daniel.helpdeskbff.config;

import br.com.daniel.helpdeskbff.decoder.RetrieveMessageErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    ErrorDecoder getDecoder() {
        return new RetrieveMessageErrorDecoder();
    }
}