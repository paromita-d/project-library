package org.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity @Table(name = "users")
@Data @Builder @NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_type")
    private String userType;
    @Column(name = "pwd")
    private String password;
}
