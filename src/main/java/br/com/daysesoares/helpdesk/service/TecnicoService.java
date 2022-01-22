package br.com.daysesoares.helpdesk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.daysesoares.helpdesk.domain.Tecnico;
import br.com.daysesoares.helpdesk.domain.dto.TecnicoDTO;
import br.com.daysesoares.helpdesk.repository.TecnicoRepository;
import br.com.daysesoares.helpdesk.service.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: "+id));
	}

	public List<TecnicoDTO> findAll() {
		List<Tecnico> list = repository.findAll();
		return list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
	}
	
}
