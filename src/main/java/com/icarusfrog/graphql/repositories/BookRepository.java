package com.icarusfrog.graphql.repositories;

import com.icarusfrog.graphql.models.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookRepository {
    private List<Book> bookList = List.of(
            new Book("book-01", "Book 01 Title", 33, "author-01"),
            new Book("book-02", "Book 02 Title", 55, "author-01"),
            new Book("book-03", "Book 03 Title", 45, "author-03")
    );

    public Optional<Book> getBookById(String bookId) {
        if(Objects.isNull(bookId)) {
            return Optional.empty();
        }
        return bookList.stream().filter(book -> bookId.contentEquals(book.id())).findFirst();
    }
}
