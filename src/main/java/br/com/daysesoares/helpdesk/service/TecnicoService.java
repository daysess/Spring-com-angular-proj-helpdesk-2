package br.com.daysesoares.helpdesk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.daysesoares.helpdesk.domain.Pessoa;
import br.com.daysesoares.helpdesk.domain.Tecnico;
import br.com.daysesoares.helpdesk.domain.dto.TecnicoDTO;
import br.com.daysesoares.helpdesk.repository.PessoaRepository;
import br.com.daysesoares.helpdesk.repository.TecnicoRepository;
import br.com.daysesoares.helpdesk.service.exceptions.DataIntegrityViolationException;
import br.com.daysesoares.helpdesk.service.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: "+id));
	}

	public List<TecnicoDTO> findAll() {
		List<Tecnico> list = repository.findAll();
		return list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
	}

	public Tecnico create(TecnicoDTO objDTO) {
		objDTO.setId(null);
		validaPorCpfEemail(objDTO);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		Tecnico obj = new Tecnico(objDTO);
		return repository.save(obj);
	}
	
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = findById(id);
		validaPorCpfEemail(objDTO);
		oldObj = new Tecnico(objDTO);
		return repository.save(oldObj);
	}
	
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if(obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possui ordem de serviço e não pode ser deletado!");
		}
		repository.delete(obj);
	}

	private void validaPorCpfEemail(TecnicoDTO objDTO) {
		Optional<Pessoa> objPessoa = pessoaRepository.findByCpf(objDTO.getCpf());
		if(objPessoa.isPresent() && objPessoa.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema.");
		}
		objPessoa = pessoaRepository.findByEmail(objDTO.getEmail());
		if(objPessoa.isPresent() && objPessoa.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema.");
		}
		
	}

	
}
