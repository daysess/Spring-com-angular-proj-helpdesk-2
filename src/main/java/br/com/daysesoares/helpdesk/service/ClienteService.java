package br.com.daysesoares.helpdesk.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.daysesoares.helpdesk.domain.Cliente;
import br.com.daysesoares.helpdesk.domain.Pessoa;
import br.com.daysesoares.helpdesk.domain.dto.ClienteDTO;
import br.com.daysesoares.helpdesk.repository.ClienteRepository;
import br.com.daysesoares.helpdesk.repository.PessoaRepository;
import br.com.daysesoares.helpdesk.service.exceptions.DataIntegrityViolationException;
import br.com.daysesoares.helpdesk.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: "+id));
	}

	public List<ClienteDTO> findAll() {
		List<Cliente> list = repository.findAll();
		return list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
	}

	public Cliente create(ClienteDTO objDTO) {
		objDTO.setId(null);
		validaPorCpfEemail(objDTO);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		Cliente obj = new Cliente(objDTO);
		return repository.save(obj);
	}
	
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente oldObj = findById(id);
		if(!objDTO.getSenha().equals(oldObj.getSenha())) {
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		}
		validaPorCpfEemail(objDTO);
		oldObj = new Cliente(objDTO);
		return repository.save(oldObj);
	}
	
	public void delete(Integer id) {
		Cliente obj = findById(id);
		if(obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possui ordem de serviço e não pode ser deletado!");
		}
		repository.delete(obj);
	}

	private void validaPorCpfEemail(ClienteDTO objDTO) {
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
