package br.com.daysesoares.helpdesk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.daysesoares.helpdesk.domain.Chamado;
import br.com.daysesoares.helpdesk.domain.Cliente;
import br.com.daysesoares.helpdesk.domain.Tecnico;
import br.com.daysesoares.helpdesk.domain.enums.Perfil;
import br.com.daysesoares.helpdesk.domain.enums.Prioridade;
import br.com.daysesoares.helpdesk.domain.enums.Status;
import br.com.daysesoares.helpdesk.repository.ChamadoRepository;
import br.com.daysesoares.helpdesk.repository.ClienteRepository;
import br.com.daysesoares.helpdesk.repository.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ChamadoRepository chamadoRepository;

	public void instanciaDB() {
		Tecnico tec1 = new Tecnico(null, "Dayse Soares", "946.215.520-82", "dayse@gmail.com", "123");
		tec1.addPerfil(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Jonas", "312.496.800-00","jonas@gmail.com", "123");
		cli1.addPerfil(Perfil.CLIENTE);
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "primeiro chamado", tec1, cli1);
		
		tecnicoRepository.save(tec1);
		clienteRepository.save(cli1);
		chamadoRepository.save(c1);
	}
	
}
