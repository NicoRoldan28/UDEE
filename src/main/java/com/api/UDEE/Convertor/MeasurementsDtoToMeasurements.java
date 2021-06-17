package com.api.UDEE.Convertor;

import com.api.UDEE.domain.Measurement;
import com.api.UDEE.dto.MeasurementsDto;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

public class MeasurementsDtoToMeasurements implements Converter<MeasurementsDto, Measurement>{

    private ModelMapper modelMapper;

    @Override
    public Measurement convert(MeasurementsDto source) {
        return modelMapper.map(source,Measurement.class);
    }
}
