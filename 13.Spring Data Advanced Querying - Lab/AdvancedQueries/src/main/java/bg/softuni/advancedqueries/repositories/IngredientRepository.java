package bg.softuni.advancedqueries.repositories;

import bg.softuni.advancedqueries.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByNameStartsWith(String start);
    List<Ingredient> findByNameInOrderByPriceAsc(List<String> ingredients);
}
