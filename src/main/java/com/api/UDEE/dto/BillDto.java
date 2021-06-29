package com.api.UDEE.dto;

import com.api.UDEE.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BillDto {

    Address address;
    String number_measurer;
    Integer total;
    Boolean paid;

}
