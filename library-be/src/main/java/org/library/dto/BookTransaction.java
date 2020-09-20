package org.library.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class BookTransaction {
    private Integer id;
    private UserTransaction userTransaction;
    private Book book;
    private Boolean returned;
    private Boolean overdue;
}
