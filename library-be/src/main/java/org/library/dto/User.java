package org.library.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class User {
    private Integer id;
    private String userName;
    private String userType;
    private String pwd;
}
