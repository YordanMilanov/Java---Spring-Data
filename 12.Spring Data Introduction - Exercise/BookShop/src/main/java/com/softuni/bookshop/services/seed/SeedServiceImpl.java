package com.softuni.bookshop.services.seed;

import com.softuni.bookshop.domain.entities.Author;
import com.softuni.bookshop.domain.entities.Book;
import com.softuni.bookshop.domain.entities.Category;
import com.softuni.bookshop.domain.enums.AgeRestriction;
import com.softuni.bookshop.domain.enums.EditionType;
import com.softuni.bookshop.services.author.AuthorService;
import com.softuni.bookshop.services.book.BookService;
import com.softuni.bookshop.services.category.CategoryService;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.softuni.bookshop.constants.filePaths.*;

@Service
public class SeedServiceImpl implements SeedService {

    private final AuthorService authorService;
    private final BookService bookService;
    private final CategoryService categoryService;

    public SeedServiceImpl(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }


    @Override
    public void seedAuthors() throws IOException {
        if (!this.authorService.isDataSeeded()) {
            this.authorService.seedAuthors(Files.readAllLines(Path.of(RESOURCE_URL + AUTHOR_FILE_NAME))
                    .stream()
                    .filter(s -> !s.isBlank())
                    .map(s -> Author.builder()
                            .firstName(s.split(" ")[0])
                            .lastName(s.split(" ")[1])
                            .build()).collect(Collectors.toList()));
        }
    }

    @Override
    public void seedBooks() throws IOException {
        final List<Book> books = Files.readAllLines(Path.of(RESOURCE_URL + BOOK_FILE_NAME))
                .stream()
                .filter(s -> !s.isBlank())
                .map(row -> {
                    String[] data = row.split("\\s+");
                    Author author = this.authorService.getRandomAuthor();

                    EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];

                    LocalDate releaseDate = LocalDate.parse(data[1],
                            DateTimeFormatter.ofPattern("d/M/yyyy"));

                    int copies = Integer.parseInt(data[2]);

                    BigDecimal price = new BigDecimal(data[3]);

                    AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(data[4])];

                    String title = Arrays.stream(data).skip(5)
                            .collect(Collectors.joining(" "));

                    Set<Category> categories = categoryService.getRandomCategories();


                    return Book.builder()
                            .title(title)
                            .editionType(editionType)
                            .price(price)
                            .releaseDate(releaseDate)
                            .ageRestriction(ageRestriction)
                            .author(author)
                            .categories(this.categoryService.getRandomCategories())
                            .copies(copies)
                            .build();
                }).collect(Collectors.toList());
        this.bookService.seedBooks(books);
    }

    @Override
    public void seedCategories() throws IOException {
        if (!this.categoryService.isDataSeeded()) {

            this.categoryService.seedCategories(Files.readAllLines(Path.of(RESOURCE_URL + CATEGORY_FILE_NAME))
                    .stream()
                    .filter(s -> !s.isBlank())
                    .map(name -> Category.builder()
                            .name(name)
                            .build())
                    .collect(Collectors.toList()));
        }
    }
}
