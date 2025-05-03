package com.thinh.bookpresentationservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "authors")
public class Author implements Serializable {
    private Long id;

    @NotNull(message = "Name is required")
    @Size(min = 1, max = 500, message = "Name must be between 1 and 500 characters")
    private String name;

    @Size(max = 2000, message = "Bio cannot exceed 2000 characters")
    private String biography;
}
