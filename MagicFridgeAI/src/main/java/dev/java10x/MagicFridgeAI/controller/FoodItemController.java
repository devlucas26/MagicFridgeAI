package dev.java10x.MagicFridgeAI.controller;

import dev.java10x.MagicFridgeAI.model.FoodItem;
import dev.java10x.MagicFridgeAI.service.FoodItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodItemController {

    private FoodItemService foodItemService;

    public FoodItemController(FoodItemService foodItemService){
        this.foodItemService = foodItemService;
    }

    // GET listar todos
    @GetMapping("/listar")
    public ResponseEntity<List<FoodItem>> listar(){
        List<FoodItem> itens = foodItemService.listar();
        if(itens.isEmpty()){
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(itens);
    }
    // GET listar por id fornecido
    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listaPorId(@PathVariable Long id){
        if(foodItemService.listarPorId(id) != null){
            return ResponseEntity.ok()
                    .body(foodItemService.listarPorId(id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("numero " + id + " não encontrado no banco de dados");
    }
    // POST
    @PostMapping("/criar")
    public ResponseEntity<FoodItem> criar(@RequestBody FoodItem foodItem){
        return ResponseEntity.ok()
                .body(foodItemService.salvar(foodItem));
    }
    // PUT
    @PutMapping("/altera/{id}")
    public ResponseEntity<?> alterar(@PathVariable Long id, @RequestBody FoodItem foodItem){
        if (foodItemService.listarPorId(id) != null){
            return ResponseEntity.ok()
                    .body(foodItemService.alterar(id, foodItem));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Item " + id + " alterado por não estar presente no banco");
    }
    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleta(@PathVariable Long id){
        if (foodItemService.listarPorId(id) != null){
            foodItemService.remove(id);
            return ResponseEntity.ok()
                    .body("Item deletado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Item " + id + " não deletado por não ter sido encontrado no banco");
    }
    @GetMapping("/teste")
    public String teste(){
        return "teste de rota, apenas a frase teste deveria ser impressa aqui";
    }
    @PatchMapping("/alteraparte/{id}")
    public ResponseEntity<?> alterarParcial(@PathVariable Long id, @RequestBody FoodItem foodItem){
        if(foodItemService.listarPorId(id) != null){
            return ResponseEntity.ok()
                    .body(foodItemService.alterarParcial(id, foodItem));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(id + " conteudo não encontrado");
    }
    @GetMapping("/homepage")
    public String paginaInicial(){
        return "PÁGINA INICIAL DA APLICAÇÃO";
    }
}
