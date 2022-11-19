package bg.softuni.advancedqueries.services;

import bg.softuni.advancedqueries.entities.Shampoo;

import java.util.List;


public interface ShampooService {
    List<Shampoo> findByBrand(String brand);

    List<Shampoo> findByBrandAndSize(String brand, String size);
}
