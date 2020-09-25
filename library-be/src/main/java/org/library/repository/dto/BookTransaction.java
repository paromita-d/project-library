package org.library.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "books_transactions")
@Data @Builder @NoArgsConstructor
@AllArgsConstructor
public class BookTransaction {
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_transaction_id", referencedColumnName = "id")
    private UserTransaction userTransaction;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    private Boolean returned;

    private Boolean overdue;
}
