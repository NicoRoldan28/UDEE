package com.api.UDEE.dto;

import com.api.UDEE.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MeterDto {

    Address address;
    String serialNumber;
    String password;
}
