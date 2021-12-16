package br.com.daysesoares.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.daysesoares.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{

}
