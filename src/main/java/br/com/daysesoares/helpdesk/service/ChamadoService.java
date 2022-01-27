package br.com.daysesoares.helpdesk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.daysesoares.helpdesk.domain.Chamado;
import br.com.daysesoares.helpdesk.domain.dto.ChamadoDTO;
import br.com.daysesoares.helpdesk.repository.ChamadoRepository;
import br.com.daysesoares.helpdesk.service.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository chamadoRepository;
	
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = chamadoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: "+id));
	}


	public List<ChamadoDTO> findAll() {
		List<Chamado> list = chamadoRepository.findAll();
		return list.stream().map(obj -> new ChamadoDTO(obj)).collect(Collectors.toList());
	}
	
}
