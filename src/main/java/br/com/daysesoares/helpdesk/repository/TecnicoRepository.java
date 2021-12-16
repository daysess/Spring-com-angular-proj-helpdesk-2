package br.com.daysesoares.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.daysesoares.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{

}
