package com.api.UDEE.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table (name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    private String username;

    private String password;

    private String name;

    private String last_name;

    private String email;

    @Column(name = "user_type")
    @AccessType(AccessType.Type.PROPERTY)
    TypeUser typeUser;

    @JsonIgnore
    @OneToMany(mappedBy = "userClient",fetch = FetchType.EAGER)
    @Column(name = "address_id")
    private List<Address> addressList;
}
