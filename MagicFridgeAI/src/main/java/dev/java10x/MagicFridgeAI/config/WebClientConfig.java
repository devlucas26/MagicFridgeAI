package dev.java10x.MagicFridgeAI.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${chatgpt.api.url:https://api.openai.com/v1/chat/completions}")
    private String chatGptApiUrl;

    @Bean
    public WebClient webClient() {
        // Mudança essencial para a versão atual: criamos o Builder diretamente aqui,
        // eliminando a necessidade do Spring tentar injetar algo que ele não achou.
        return WebClient.builder()
                .baseUrl(chatGptApiUrl)
                .build();
    }
}