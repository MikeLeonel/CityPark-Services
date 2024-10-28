package com.devsolutions.CityParkServices.web.dto.mapper;


import com.devsolutions.CityParkServices.entity.Vaga;
import com.devsolutions.CityParkServices.web.dto.VagaCreateDto;
import com.devsolutions.CityParkServices.web.dto.VagaResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)

public class VagaMapper {

    public static Vaga toVaga(VagaCreateDto dto) {
        return new ModelMapper().map(dto, Vaga.class);
    }

    public static VagaResponseDto toDto(Vaga vaga) {
        return new ModelMapper().map(vaga, VagaResponseDto.class);

    }


}
