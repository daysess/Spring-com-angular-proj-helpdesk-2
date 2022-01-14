package br.com.daysesoares.helpdesk.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.daysesoares.helpdesk.domain.Tecnico;
import br.com.daysesoares.helpdesk.service.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResouce {
	
	@Autowired
	private TecnicoService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Tecnico> findById(@PathVariable Integer id){
		Tecnico obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
}
