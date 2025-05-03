package com.thinh.bookpresentationservice.service;

import com.thinh.bookpresentationservice.api.BookResponse;
import com.thinh.bookpresentationservice.common.ApiResponse;
import com.thinh.bookpresentationservice.common.Paging;
import com.thinh.bookpresentationservice.mapper.BookResponseMapper;
import com.thinh.bookpresentationservice.repository.BookPresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookPresentationService {

    @Autowired
    private BookPresentationRepository bookPresentationRepository;

    @Autowired
    private BookResponseMapper bookResponseMapper;

    public Mono<Paging<BookResponse>> getBooks(int page, int size) {
        return bookPresentationRepository.getBooks(page, size).map(bookPage -> {
            List<BookResponse> responseContent = bookPage.getContent().stream()
                    .map(BookResponseMapper::fromBook)
                    .collect(Collectors.toList());
            return new Paging<>(
                    responseContent,
                    bookPage.getPageable(),
                    bookPage.getTotalPages()
            );
        });
    }

    public Mono<BookResponse> getBookDetail(Long bookId) {
        return bookPresentationRepository.getBookDetail(bookId)
                .map(BookResponseMapper::fromBook);
    }
}
