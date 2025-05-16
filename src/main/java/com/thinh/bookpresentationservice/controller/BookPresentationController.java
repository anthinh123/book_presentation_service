package com.thinh.bookpresentationservice.controller;


import com.thinh.bookpresentationservice.api.BookResponse;
import com.thinh.bookpresentationservice.common.ApiResponse;
import com.thinh.bookpresentationservice.common.Paging;
import com.thinh.bookpresentationservice.service.BookPresentationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/presentation/books")
@RequiredArgsConstructor
public class BookPresentationController {

    private final BookPresentationService bookPresentationService;

    String userId = "123";

    @GetMapping
    public Mono<ResponseEntity<ApiResponse<Paging<BookResponse>>>> getBookPresentation(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return bookPresentationService.getBooks(page, size).map(ApiResponse::success);
    }

    @GetMapping("/{bookId}")
    public Mono<ResponseEntity<ApiResponse<BookResponse>>> getBookDetail(@PathVariable Long bookId) {
        return bookPresentationService.getBookDetail(bookId, userId).map(bookResponse -> ApiResponse.success(bookResponse));
    }

}
