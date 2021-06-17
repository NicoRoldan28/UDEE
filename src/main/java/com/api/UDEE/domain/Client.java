package com.api.UDEE.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Integer id;

    private String name;

    private String last_name;

    private String email;

}
