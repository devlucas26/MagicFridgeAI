package dev.java10x.MagicFridgeAI.service;

import dev.java10x.MagicFridgeAI.model.FoodItem;
import dev.java10x.MagicFridgeAI.repository.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemService {

    private FoodItemRepository foodItemRepository;

    public FoodItemService(FoodItemRepository foodItemRepository){
        this.foodItemRepository = foodItemRepository;
    }

    public List<FoodItem> listar(){
        return foodItemRepository.findAll();
    }
    public FoodItem listarPorId(Long id){
        return foodItemRepository.findById(id).orElse(null);
    }
    public FoodItem salvar(FoodItem foodItem){
        return foodItemRepository.save(foodItem);
    }
    public FoodItem alterar(Long id, FoodItem foodItem){
        if (foodItemRepository.existsById(id)){
            foodItem.setId(id);
            return foodItemRepository.save(foodItem);
        }
        return null;
    }
    public void remove(Long id){
        foodItemRepository.deleteById(id);
    }
    public FoodItem alterarParcial(Long id, FoodItem foodItem){
        var item = foodItemRepository.findById(id);

        if(item.isPresent()){
            FoodItem cadastrado = item.get();

            if (foodItem.getNome() != null){
                cadastrado.setNome(foodItem.getNome());
            }
            if (foodItem.getCategoria() != null){
                cadastrado.setCategoria(foodItem.getCategoria());
            }
            if(foodItem.getQuantidade() != null){
                cadastrado.setQuantidade(foodItem.getQuantidade());
            }
            if (foodItem.getDataVencimento() != null){
                cadastrado.setDataVencimento(foodItem.getDataVencimento());
            }
            return foodItemRepository.save(cadastrado);
        }
        throw new RuntimeException("Item não encontrado");
    }
}
