package bg.softuni.advancedqueries.services;

import bg.softuni.advancedqueries.entities.Ingredient;
import bg.softuni.advancedqueries.entities.Shampoo;

import java.math.BigDecimal;
import java.util.List;


public interface ShampooService {
    List<Shampoo> findByBrand(String brand);

    List<Shampoo> findByBrandAndSize(String brand, String size);

    List<Shampoo> findBySize(String size);

    List<Shampoo> findByPriceOrderByPriceDesc(BigDecimal price);

    double countByPriceLessThan(BigDecimal price);

    List<Shampoo> findByIngredient(String ingredient);

    List<Shampoo>findAllShampoosWithIngredientsLessThan(Integer count);
}
