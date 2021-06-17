package com.api.UDEE.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "measurements")

public class Measurement {
    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    @Column(name = "id_measurement")
    private Integer id;

    /*@OneToOne
    private Bill bill;
*/

    @OneToOne
    @JoinColumn(name = "id_meter")
    private Meter meter;

    //private String measurement;

    private String date;

}
