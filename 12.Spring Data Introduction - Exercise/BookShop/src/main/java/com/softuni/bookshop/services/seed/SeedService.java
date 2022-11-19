package com.softuni.bookshop.services.seed;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface SeedService {

    void seedAuthors() throws IOException;
    void seedBooks() throws IOException;
    void seedCategories() throws IOException;

    default void seedAllData() throws IOException{
        seedAuthors();
        seedBooks();
        seedCategories();
    }
}
