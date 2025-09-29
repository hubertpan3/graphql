package com.icarusfrog.graphql.gqlControllers;

import com.icarusfrog.graphql.models.Author;
import com.icarusfrog.graphql.models.Book;
import com.icarusfrog.graphql.repositories.AuthorRepository;
import com.icarusfrog.graphql.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class BookController {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @QueryMapping
    public Book bookById(@Argument("id") String bookId) {
        return bookRepository.getBookById(bookId).orElse(null);
    }

    @SchemaMapping("author")
    public Author authorByBook(Book book) {
        return authorRepository.getAuthorById(book.authorId()).orElse(null);
    }
}
