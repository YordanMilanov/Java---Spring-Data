package com.softuni.bookshop.repositories;

import com.softuni.bookshop.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//in the <> brackets in the JpaRepository<we set the class for Which te repository is, type of the ID column>, which the ID type column here is Long because of the
//private Long id; in the BaseEntity which sets the ID type for all its child classes including "Category" also.
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
