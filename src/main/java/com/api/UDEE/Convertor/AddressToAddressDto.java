package com.api.UDEE.Convertor;

import org.springframework.stereotype.Component;

import com.api.UDEE.dto.AddressDto;
import com.api.UDEE.domain.Address;

@Component
public class AddressToAddressDto {

    public AddressDto convertToDto(Address address){
        AddressDto addresDto= new AddressDto();
        addresDto.setRate(address.getRate());
        addresDto.setStreet(address.getStreet());
        addresDto.setNumber(address.getNumber());

        return addresDto;
    }
}
