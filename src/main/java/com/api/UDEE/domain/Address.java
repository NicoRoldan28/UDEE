package com.api.UDEE.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "rate_id")
    private Rate rate;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value="client_id")
    @JoinColumn(name="client_id")
    private Client client;*/

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User userClient;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

}
