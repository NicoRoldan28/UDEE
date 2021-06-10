package com.api.UDEE.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rates")
public class Rate {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    private Integer id;

    @Column(name = "price")
    private Float price;
}
