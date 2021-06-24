package com.api.UDEE.Convertor;

import com.api.UDEE.domain.Meter;
import com.api.UDEE.dto.AddressDto;
import com.api.UDEE.dto.MeterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class MeterToMeterDto {

    @Autowired
    private AddressToAddressDto dto;

public MeterDto convertToDto(Meter meter){
    MeterDto meterDto= new MeterDto();
    meterDto.setAddress(dto.convertToDto(meter.getAddress()));
    meterDto.setSerialNumber(meter.getSerialNumber());

    return meterDto;
}

    public Page<MeterDto> convertPageToDto(Page<Meter> page)
    { return page.map(this::convertToDto);
    }
}
