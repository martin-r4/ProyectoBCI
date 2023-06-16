package com.bci.proyectobci.entity;

import com.bci.proyectobci.controller.dto.PhoneDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "phones")
public class PhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private Integer number;
    @Column(name = "city_code")
    private Integer cityCode;
    @Column(name = "country_code")
    private String countryCode;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private UserEntity usersId;

    public static PhoneEntity fromDtoToEntity(PhoneDto dto) {
        return PhoneEntity.builder().cityCode(dto.getCityCode()).number(dto.getNumber())
                .countryCode(dto.getCountryCode()).build();
    }
}
