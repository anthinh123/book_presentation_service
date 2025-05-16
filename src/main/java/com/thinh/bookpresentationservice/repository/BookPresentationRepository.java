package com.thinh.bookpresentationservice.repository;

import com.thinh.bookpresentationservice.common.ApiResponse;
import com.thinh.bookpresentationservice.common.Paging;
import com.thinh.bookpresentationservice.model.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class BookPresentationRepository {
    @Value("${services.book_catalog.path}")
    private String bookCatalogPath;

    private final WebClient webClient;

    public BookPresentationRepository(WebClient.Builder webClientBuilder,  @Value("${services.book_catalog.host}") String host) {
        this.webClient = webClientBuilder.baseUrl(host).build();
    }

    public Mono<Paging<Book>> getBooks(int page, int size) {

        ParameterizedTypeReference<ApiResponse<Paging<Book>>> responseType =
                new ParameterizedTypeReference<>() {
                };

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(bookCatalogPath)
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseType)
                .flatMap(apiResponse -> {
                    if (apiResponse.getCode() == HttpStatus.OK.value() && apiResponse.getData() != null) {
                        return Mono.just(apiResponse.getData());
                    } else {
                        String errorMessage = apiResponse.getMessage() != null ? apiResponse.getMessage() : "Catalog service returned non-OK status or null data";
                        return Mono.error(new RuntimeException(errorMessage + " (Code: " + apiResponse.getCode() + ")"));
                    }
                })
                .log("response-from-book-catalog-service");
    }

    public Mono<Book> getBookDetail(long bookId) {
        ParameterizedTypeReference<ApiResponse<Book>> responseType =
                new ParameterizedTypeReference<>() {
                };

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(bookCatalogPath + "/" + bookId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseType)
                .flatMap(apiResponse -> {
                    if (apiResponse.getCode() == HttpStatus.OK.value() && apiResponse.getData() != null) {
                        return Mono.just(apiResponse.getData());
                    } else {
                        String errorMessage = apiResponse.getMessage() != null ? apiResponse.getMessage() : "Catalog service (getBookDetail " + bookId + ") returned non-OK status or null data";
                        return Mono.error(new RuntimeException(errorMessage + " (Code: " + apiResponse.getCode() + ")"));
                    }
                })
                .log("response-from-book-catalog-service-getBookDetail");

    }

}
