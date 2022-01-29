package br.com.daysesoares.helpdesk.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.daysesoares.helpdesk.domain.Chamado;
import br.com.daysesoares.helpdesk.domain.Cliente;
import br.com.daysesoares.helpdesk.domain.Tecnico;
import br.com.daysesoares.helpdesk.domain.dto.ChamadoDTO;
import br.com.daysesoares.helpdesk.domain.enums.Prioridade;
import br.com.daysesoares.helpdesk.domain.enums.Status;
import br.com.daysesoares.helpdesk.repository.ChamadoRepository;
import br.com.daysesoares.helpdesk.service.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService;
	
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = chamadoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: "+id));
	}


	public List<ChamadoDTO> findAll() {
		List<Chamado> list = chamadoRepository.findAll();
		return list.stream().map(obj -> new ChamadoDTO(obj)).collect(Collectors.toList());
	}


	public Chamado create(ChamadoDTO objDTO) {
		return chamadoRepository.save(newChamado(objDTO));
	}
	
	private Chamado newChamado(ChamadoDTO objDTO) {
		Tecnico tecnico = tecnicoService.findById(objDTO.getIdTecnico());
		Cliente cliente = clienteService.findById(objDTO.getIdCliente());
		
		Chamado chamado = new Chamado();
		if(objDTO.getId() != null) {
			chamado.setId(objDTO.getId());
		}
		
		if(Status.ENCERRADO.getCodigo() == objDTO.getIdStatus()) {
			chamado.setDataFechamento(LocalDate.now());
		}
		
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(objDTO.getIdPrioridade()));
		chamado.setStatus(Status.toEnum(objDTO.getIdStatus()));
		chamado.setTitulo(objDTO.getTitulo());
		chamado.setObservacoes(objDTO.getObservacoes());
		
		return chamado;
	}


	public Chamado update(Integer id, ChamadoDTO objDTO) {
		objDTO.setId(id);
		Chamado oldObj = findById(id);
		oldObj = newChamado(objDTO);
		return chamadoRepository.save(oldObj);
	}
	
}
