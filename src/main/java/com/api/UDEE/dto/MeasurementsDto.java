package com.api.UDEE.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class MeasurementsDto {
    String serialNumber;
    float value;
    String date;
    String password;
}
