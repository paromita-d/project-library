package org.library.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data @Builder
public class UserTransaction {
    private Integer id;
    private Date tranDate;
    private User user;
    private Integer checkOutQty;
    private Date dueDate;
    private String status;
}
