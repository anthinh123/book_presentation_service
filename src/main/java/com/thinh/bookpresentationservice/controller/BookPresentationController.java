package com.thinh.bookpresentationservice.controller;


import com.thinh.bookpresentationservice.api.BookResponse;
import com.thinh.bookpresentationservice.common.ApiResponse;
import com.thinh.bookpresentationservice.common.Paging;
import com.thinh.bookpresentationservice.service.BookPresentationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/presentation/books")
@RequiredArgsConstructor
public class BookPresentationController {

    private final BookPresentationService bookPresentationService;

    @GetMapping
    public Mono<ResponseEntity<ApiResponse<Paging<BookResponse>>>> getBookPresentation(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return bookPresentationService.getBooks(page, size)
                .map(ApiResponse::success);
    }

}
