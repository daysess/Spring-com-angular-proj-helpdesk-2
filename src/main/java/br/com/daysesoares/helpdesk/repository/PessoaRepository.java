package br.com.daysesoares.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.daysesoares.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

}
