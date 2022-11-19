package bg.softuni.advancedqueries.services;

import bg.softuni.advancedqueries.entities.Ingredient;

import java.util.List;

public interface IngredientService {

    List<Ingredient> selectByName(String start);
    List<Ingredient> findByNameInOrderByPriceAsc(List<String> ingredients);
}
