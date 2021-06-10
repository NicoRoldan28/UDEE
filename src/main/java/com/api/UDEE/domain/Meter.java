package com.api.UDEE.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meters")
public class Meter {
    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    @Column(name = "id_meter")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_address")
    private Address address;

    private String serial_number;

    private String password;

    @OneToOne
    @JoinColumn(name = "id_brand")
    private Brand brand;

    @OneToOne
    @JoinColumn(name = "id_model")
    private Model model;
}
