package com.api.UDEE.Convertor;

import com.api.UDEE.domain.Address;
import com.api.UDEE.domain.Bill;
import com.api.UDEE.dto.AddressDto;
import com.api.UDEE.dto.BillDto;
import com.api.UDEE.service.MeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillToBillDto {

    @Autowired
    private MeterService meterService;

    public BillDto convertToDto(Bill bill){
        BillDto billDto= new BillDto();
        billDto.setAddress(bill.getAddress());
        billDto.setPaid(bill.getPaid());
        billDto.setNumber_measurer(bill.getNumber_measurer());
        billDto.setTotal(bill.getTotal());

        return billDto;
    }
}
