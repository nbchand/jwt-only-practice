package com.nabin.jwtonly.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author Narendra
 * @version 1.0
 * @since 2022-05-27
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User_SEQ_GEN")
    @SequenceGenerator(name = "User_SEQ_GEN", sequenceName = "User_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;
}
