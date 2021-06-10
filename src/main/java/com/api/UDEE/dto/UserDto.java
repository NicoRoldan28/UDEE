package com.api.UDEE.dto;

import com.api.UDEE.domain.TypeUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
     int id;
     String username;
}
