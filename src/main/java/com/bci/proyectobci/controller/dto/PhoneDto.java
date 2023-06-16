package com.bci.proyectobci.controller.dto;

import com.bci.proyectobci.entity.PhoneEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDto {

    private Integer number;
    private Integer cityCode;
    private String countryCode;

    public static PhoneDto phoneDtoToEntity(PhoneEntity entity) {
        return PhoneDto.builder().cityCode(entity.getCityCode()).number(entity.getNumber())
                .countryCode(entity.getCountryCode()).build();
    }
}
