package org.brasil.prev.rest.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.brasil.prev.rest.entity.Cliente;
import org.brasil.prev.rest.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class ClienteController {
	@Autowired
	private ClienteService service;
	
	@GetMapping(path="/all")
	public @ResponseBody List<Cliente> getAllCustomers() {
		return service.getAllCustomers();
	}
	
	@GetMapping(path="/{id}")
	public @ResponseBody Cliente getCustomerById(@PathVariable String id) {
		try {
			return service.getCustomerById(Long.parseLong(id));
		} catch(NumberFormatException ex) {
			return null;
		}
	}
	
	@PostMapping
	public Cliente addCustomer(@Valid @RequestBody Cliente cliente) {
		return service.saveCustomer(cliente);
	}
	
	@PutMapping(path="/{id}")
	public ResponseEntity<Cliente> updateCustomer(@PathVariable Long id, @Valid @RequestBody Cliente cliente){
		Cliente c = service.getCustomerById(id);
		
		if(c == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		
		c.setRua(cliente.getRua());
	    c.setCidade(cliente.getCidade());
	    c.setBairro(cliente.getBairro());
	    c.setCep(cliente.getCep());
	    c.setEstado(cliente.getEstado());
		
		return ResponseEntity.ok(service.saveCustomer(c));
	}
	
	@DeleteMapping(path="/{id}")
	public Map<String, Boolean> deleteCustomer(@PathVariable Long id) {
		Map<String, Boolean> response = new HashMap<>();
		Cliente c = service.getCustomerById(id);
		
		if(c == null) {
			response.put("excluido", Boolean.FALSE);
			return response;
		}
		service.deleteCustomer(c);
		
	
		response.put("excluido", Boolean.TRUE);
		return response;
	}
}
