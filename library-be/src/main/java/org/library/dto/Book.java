package org.library.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Book {
    private Integer id;
    private String bookName;
    private String author;
    private String description;
    private Integer quantity;
    private Integer availability;
}
