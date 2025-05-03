package com.thinh.bookpresentationservice.mapper;

import com.thinh.bookpresentationservice.api.BookResponse;
import com.thinh.bookpresentationservice.model.Author;
import com.thinh.bookpresentationservice.model.Book;
import com.thinh.bookpresentationservice.model.Category;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookResponseMapper {

    public static BookResponse fromBook(Book book) {
        // Handle null input book gracefully
        if (book == null) {
            return null;
        }

        BookResponse response = new BookResponse();

        // Direct field mapping
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setDescription(book.getDescription());
        response.setPrice(book.getPrice());
        // Assuming publicationDate in Book model is already the desired String format
        response.setPublicationDate(book.getPublicationDate());

        // Map List<Author> to List<String> (author names)
        response.setAuthors(
                Optional.ofNullable(book.getAuthors()) // Safely handle null list
                        .orElse(Collections.emptyList()) // Default to empty list if null
                        .stream()
                        .filter(Objects::nonNull) // Ensure author object itself is not null
                        .map(Author::getName)     // Extract author name
                        .filter(Objects::nonNull) // Ensure author name is not null
                        .collect(Collectors.toList()) // Collect names into a List<String>
        );

        // Map List<Category> to List<String> (category names)
        response.setCategory( // Field name is 'category' in BookResponse
                Optional.ofNullable(book.getCategories()) // Safely handle null list
                        .orElse(Collections.emptyList()) // Default to empty list if null
                        .stream()
                        .filter(Objects::nonNull) // Ensure category object itself is not null
                        .map(Category::getName)   // Extract category name
                        .filter(Objects::nonNull) // Ensure category name is not null
                        .collect(Collectors.toList()) // Collect names into a List<String>
        );

        return response;
    }
}
