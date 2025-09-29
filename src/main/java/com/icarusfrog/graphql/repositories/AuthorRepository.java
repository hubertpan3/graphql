package com.icarusfrog.graphql.repositories;

import com.icarusfrog.graphql.models.Author;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthorRepository {
    private List<Author> authors = List.of(
            new Author("author-01", "a01fn", "a01ln"),
            new Author("author-02", "a02fn", "a02ln"),
            new Author("author-03", "a03fn", "a03ln")
    );

    public Optional<Author> getAuthorById(String authorId) {
        if(Objects.isNull(authorId)) {
            return Optional.empty();
        }
        return authors.stream().filter(author -> authorId.contentEquals(author.authorId())).findFirst();
    }
}
