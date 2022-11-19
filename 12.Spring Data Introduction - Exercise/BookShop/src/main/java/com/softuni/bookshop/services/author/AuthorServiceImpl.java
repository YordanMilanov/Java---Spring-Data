package com.softuni.bookshop.services.author;

import com.softuni.bookshop.domain.entities.Author;
import com.softuni.bookshop.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors(List<Author> authors) {
        this.authorRepository.saveAll(authors);
    }

    @Override
    public boolean isDataSeeded() {
        return this.authorRepository.count() > 0;
    }

    //find random author
    @Override
    public Author getRandomAuthor() {

        final long count = this.authorRepository.count();
        if(count != 0) {
            final long randomAuthorId = new Random().nextInt((int) count) + 1L;

        return this.authorRepository.findAuthorById(randomAuthorId).orElseThrow(NoSuchElementException::new);
        }

        throw new RuntimeException();
    }
}
