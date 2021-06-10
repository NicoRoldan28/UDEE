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
@Table(name = "brands")
public class Brand {


    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    @Column(name = "id_brand")
    private Integer id;
    /*
    * @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Integer id;*/

    private String name;
}
