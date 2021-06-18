package com.api.UDEE.dto;

import com.api.UDEE.domain.Rate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AddressDto {
    Rate rate;
    String street;
    Integer number;
}
