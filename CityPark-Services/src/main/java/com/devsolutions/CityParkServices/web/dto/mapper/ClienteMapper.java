package com.devsolutions.CityParkServices.web.dto.mapper;

import com.devsolutions.CityParkServices.entity.Cliente;
import com.devsolutions.CityParkServices.web.dto.ClienteCreateDto;
import com.devsolutions.CityParkServices.web.dto.ClienteResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteMapper {

    public static Cliente toCliente(ClienteCreateDto dto) {
        return  new ModelMapper().map(dto, Cliente.class);
    }

    public static ClienteResponseDto toDto(Cliente cliente) {
        return  new ModelMapper().map(cliente, ClienteResponseDto.class);
    }

}
