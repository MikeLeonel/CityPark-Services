package com.devsolutions.CityParkServices.repository;

import com.devsolutions.CityParkServices.entity.Cliente;
import com.devsolutions.CityParkServices.repository.projection.ClienteProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository <Cliente, Long> {

    @Query("select c from Cliente c")
    Page<ClienteProjection> findAllPageable(Pageable pageable);

    Cliente findByUsuarioId(Long id);

    Optional <Cliente> findByCpf(String cpf);

}


