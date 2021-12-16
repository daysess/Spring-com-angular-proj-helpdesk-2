package br.com.daysesoares.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.daysesoares.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
