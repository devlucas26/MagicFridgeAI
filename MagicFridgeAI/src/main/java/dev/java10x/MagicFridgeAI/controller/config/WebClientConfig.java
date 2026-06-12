package dev.java10x.MagicFridgeAI.controller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${API_KEY_OPENAI}")
    private String chatGptApiUrl;

    @Bean
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder.baseUrl(chatGptApiUrl).build();
    }
}
