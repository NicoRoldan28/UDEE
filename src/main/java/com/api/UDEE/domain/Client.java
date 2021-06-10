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


    /*
    @OneToMany(mappedBy = "client")
    private List<Address> addressList;
    *@OneToMany(mappedBy = "id")
    private List<Address> address;
    * */

    private String name;

    private String last_name;

    private String email;

}
