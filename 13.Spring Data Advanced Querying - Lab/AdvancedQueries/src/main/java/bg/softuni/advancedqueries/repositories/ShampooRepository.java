package bg.softuni.advancedqueries.repositories;

import bg.softuni.advancedqueries.entities.Shampoo;
import bg.softuni.advancedqueries.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findByBrand(String brand);
    List<Shampoo> findByBrandAndSize(String brand, Size size);

    List<Shampoo> findBySize(Size parsedSizeEnumToString);


    List<Shampoo> findByPriceOrderByPriceDesc(BigDecimal price);

    double countByPriceLessThan(BigDecimal price);
}
