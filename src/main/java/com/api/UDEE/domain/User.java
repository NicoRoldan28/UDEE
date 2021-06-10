package com.api.UDEE.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor


@Table (name = "users")
public class User {
    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    private String username;

    private String password;

}
