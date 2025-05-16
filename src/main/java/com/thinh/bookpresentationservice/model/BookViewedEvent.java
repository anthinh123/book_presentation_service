package com.thinh.bookpresentationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookViewedEvent {
    private long bookId;
    private String userId;
    private String timestamp;
}
