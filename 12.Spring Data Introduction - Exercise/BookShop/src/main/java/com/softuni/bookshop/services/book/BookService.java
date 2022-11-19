package com.softuni.bookshop.services.book;

import com.softuni.bookshop.domain.entities.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookService {
    void seedBooks(List<Book> books);

    List<Book> findAllByReleaseDateAfter(LocalDate localDate);
}
