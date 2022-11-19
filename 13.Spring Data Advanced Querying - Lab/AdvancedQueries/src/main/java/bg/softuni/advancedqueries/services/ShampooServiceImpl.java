package bg.softuni.advancedqueries.services;

import bg.softuni.advancedqueries.entities.Ingredient;
import bg.softuni.advancedqueries.entities.Shampoo;
import bg.softuni.advancedqueries.entities.Size;
import bg.softuni.advancedqueries.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShampooServiceImpl implements ShampooService {

    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findByBrand(String brand) {
        return this.shampooRepository.findByBrand(brand);
    }

    @Override
    public List<Shampoo> findByBrandAndSize(String brand, String size) {
        Size parsedSizeEnumToString = Size.valueOf(size.toUpperCase());

        return this.shampooRepository.findByBrandAndSize(brand, parsedSizeEnumToString);
    }

    @Override
    public List<Shampoo> findBySize(String size) {
        Size parsedSizeEnumToString = Size.valueOf(size.toUpperCase());

        return this.shampooRepository.findBySize(parsedSizeEnumToString);
    }

    @Override
    public List<Shampoo> findByPriceOrderByPriceDesc(BigDecimal price) {
        return this.shampooRepository.findByPriceOrderByPriceDesc(price);
    }

    @Override
    public double countByPriceLessThan(BigDecimal price) {
        return this.shampooRepository.countByPriceLessThan(price);
    }


}
