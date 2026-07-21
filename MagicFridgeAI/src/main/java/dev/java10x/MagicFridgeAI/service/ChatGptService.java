package dev.java10x.MagicFridgeAI.service;

import dev.java10x.MagicFridgeAI.model.FoodItem;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
    
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ChatGptService {

    //importacao do foodItemService, para usar o metodo listar()
    private final FoodItemService service;
    //objeto do webcliente (final)
    private final WebClient webClient;
    //string com valor env da api
    private String api = System.getenv("API_KEY_OPENAI");
    //contrutor iniciando o webcliente por final, precisa ser iniciado


    public ChatGptService(FoodItemService service, WebClient webClient) {
        this.service = service;
        this.webClient = webClient;
    }

    public Mono<String> generateRecipe(){

        List<FoodItem> itens = service.listar();

        //verificar se a lista itens está vazia ou tem itens
        if (itens.isEmpty()){
            return Mono.just("Igual geladeira de pobre, só tem água");
        }
        String listaIngredientes = itens.stream()
                .map(item -> item.getNome())
                        .collect(Collectors.joining(", "));

        //Você agora é um chef de cozinha com anos de experiência e me sugerir receitas de pratos com base em ingredientes que vou te passar
        String prompt = "com base nos seguintes ingredientes disponiveis na geladeira " + listaIngredientes +
                " me sugira receitas que de pratos que usem estes mesmo ingredientes";

        Map<String, Object> requestBody = Map.of(
          "model", "gpt-5.6",
          "messages", List.of(
                  Map.of("role", "system", "content", "você é um chef de cozinha com muita experiência com culinária brasileira e europeia, com vasto conhecimento em pratos dos mais simples aos mais exoticos"),
                  Map.of("role", "user", "content", prompt)
                )
        );
        return webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + api)
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(status -> status.value() == 429, response ->
                        response.bodyToMono(String.class)
                                .flatMap(body -> {
                                    System.out.println("Corpo do erro 429: " + body);
                                    return Mono.error(new RuntimeException("Rate limit/quota: " + body));
                                })
                )
                .bodyToMono(Map.class)
                .map(response -> {
                    var choices = (List<Map<String, Object>>) response.get("choices");
                    if(choices != null && !choices.isEmpty()){
                        Map<String, Object> choice = (Map<String, Object>) choices.get(0);
                        Map<String, Object> message = (Map<String, Object>) choice.get("message")  ;
                        return message.get("content").toString();
                    }
                    return "nenhum receita gerada";
                });

    }
}
