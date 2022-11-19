package bg.softuni.advancedqueries.services;

import bg.softuni.advancedqueries.entities.Ingredient;
import bg.softuni.advancedqueries.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> selectByName(String name) {
        return this.ingredientRepository.findByNameStartsWith(name);
    }

    @Override
    public List<Ingredient> findByNameInOrderByPriceAsc(List<String> ingredients) {
        return this.ingredientRepository.findByNameInOrderByPriceAsc(ingredients);
    }
}
