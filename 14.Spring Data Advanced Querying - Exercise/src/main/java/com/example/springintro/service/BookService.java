package com.example.springintro.service;

import com.example.springintro.dto.BookInformation;
import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType type, Integer copiesNumber);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowEnd, BigDecimal highEnd);

    List<Book> findAllByReleaseDateNot (LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByTitleContainingIgnoreCase (String contains);

    Integer findAllByTitleLength (Integer length);

    List<List<String>> BooksCopiesAuthorsSum();

    BookInformation ReducedBookInformation(String title);

    int increaseBookCopies(LocalDate date, int copies);
}
