package org.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users_transactions")
@Data @Builder @NoArgsConstructor
@AllArgsConstructor
public class UserTransaction {
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "tran_date")
    private Date tranDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "checkout_qty")
    private Integer checkOutQty;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "status")
    private String status;
}
