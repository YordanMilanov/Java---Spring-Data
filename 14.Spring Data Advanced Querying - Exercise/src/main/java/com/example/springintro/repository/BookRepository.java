package com.example.springintro.repository;

import com.example.springintro.dto.BookInformation;
import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    Optional<List<Book>> findAllByEditionTypeAndCopiesLessThan(EditionType type, Integer copiesNumber);


    Optional<List<Book>> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowEnd, BigDecimal highEnd);
    Optional<List<Book>> findAllByReleaseDateNot (LocalDate date);
    Optional<List<Book>> findAllByTitleContainingIgnoreCase (String contains);

    @Query("SELECT count(b) FROM Book AS b WHERE length(b.title) > :length")
    Integer findAllByTitleLength (Integer length);

    @Query("SELECT b.author.firstName, b.author.lastName, sum(b.copies) FROM Book b GROUP BY b.author.firstName, b.author.lastName ORDER BY sum(b.copies) DESC")
    List<List<String>> BooksCopiesAuthorsSum();

    @Query("SELECT new com.example.springintro.dto.BookInformation(b.title, b.editionType, b.ageRestriction, b.price) FROM Book b WHERE b.title = :title")
    BookInformation ReducedBookInformation(String title);

    @Query("UPDATE Book b SET b.copies = b.copies + :copies WHERE b.releaseDate > :date")
    @Transactional
    @Modifying
    int increaseBookCopies(LocalDate date, int copies);
}
