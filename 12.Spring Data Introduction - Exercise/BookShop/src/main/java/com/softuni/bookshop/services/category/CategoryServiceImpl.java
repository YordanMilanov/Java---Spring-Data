package com.softuni.bookshop.services.category;

import com.softuni.bookshop.domain.entities.Category;
import com.softuni.bookshop.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void seedCategories(List<Category> categories) {
        this.categoryRepository.saveAll(categories);
    }

    @Override
    public boolean isDataSeeded() {
        return this.categoryRepository.count() > 0;
    }

    @Override
    public Set<Category> getRandomCategories() {
        final long count = this.categoryRepository.count();
        if(count != 0) {
            final long randomAuthorId = new Random().nextInt((int) count) + 1L;

            return Set.of(this.categoryRepository.findById(randomAuthorId).orElseThrow(NoSuchElementException::new));
        }

        throw new RuntimeException();
    }
}
