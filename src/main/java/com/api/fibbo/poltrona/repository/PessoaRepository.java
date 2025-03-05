package com.api.fibbo.poltrona.repository;

import com.api.fibbo.poltrona.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Pessoa findByPoltronaCodigoPoltrona(String codigoPoltrona);

}