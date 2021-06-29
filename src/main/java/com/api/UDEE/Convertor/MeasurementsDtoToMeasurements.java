package com.api.UDEE.Convertor;

import com.api.UDEE.domain.Measurement;
import com.api.UDEE.domain.Meter;
import com.api.UDEE.dto.MeasurementsDto;
import com.api.UDEE.dto.MeterDto;
import com.api.UDEE.service.MeterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MeasurementsDtoToMeasurements implements Converter<MeasurementsDto, Measurement>{

    @Autowired
    private MeterService meterService;

    @Override
    public Measurement convert(MeasurementsDto source) {

        Measurement measurement= new Measurement();
        measurement.setMeter(meterService.getMeterBySerialNumber(source.getSerialNumber()));
        measurement.setDate(source.getDate());
        measurement.setValue(source.getValue());

        return measurement;
    }

    public MeasurementsDto convertToDto(Measurement measurement){
        MeasurementsDto measurementsDto= new MeasurementsDto();
        measurementsDto.setDate(measurement.getDate());
        measurementsDto.setValue(measurement.getValue());
        measurementsDto.setSerialNumber(measurement.getMeter().getSerialNumber());
        measurementsDto.setPassword(measurement.getMeter().getPassword());

        return measurementsDto;
    }
}