package org.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Table(name = "books")
@Data @Builder @NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "book_name")
    private String bookName;
    private String author;
    private String description;
    private Integer quantity;
    private Integer availability;
}
