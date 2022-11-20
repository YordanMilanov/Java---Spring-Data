package bg.softuni.advancedqueries.repositories;

import bg.softuni.advancedqueries.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByNameStartsWith(String start);
    List<Ingredient> findByNameInOrderByPriceAsc(List<String> ingredients);

    void deleteByName(String name);

    @Query("UPDATE Ingredient AS i SET i.price = i.price * 1.1")
    @Modifying
    void updateAllPriceBy10Percent();
}
