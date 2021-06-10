package com.api.UDEE.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    @Column(name = "id_bill")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User userClient;

    @OneToOne
    @JoinColumn(name = "id_address")
    private Address address;

    private Integer number_measurer;

    private Integer measure_start;

    private Integer measure_end;

    private Integer consumption_total;

    private String date_time_start;

    private String date_time_end;

    @OneToOne
    @JoinColumn(name = "rate_id")
    private Rate rate;

    private Integer total;
}
