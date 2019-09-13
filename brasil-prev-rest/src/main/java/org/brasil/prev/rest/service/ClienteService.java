package org.brasil.prev.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.brasil.prev.rest.entity.Categoria;
import org.brasil.prev.rest.entity.Cliente;
import org.brasil.prev.rest.repository.ClienteRepository;
import org.brasil.prev.rest.security.BrasilPrevUserPrincipal;
import org.brasil.prev.rest.security.SecurityJavaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements UserDetailsService {
	private final ClienteRepository clienteRepository;
	
	@Autowired
	public ClienteService(ClienteRepository repository) {
		super();
		this.clienteRepository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Cliente cliente = this.clienteRepository.findByUserName(username);
		
		if(cliente == null)
			throw new UsernameNotFoundException("Usuário não encontrado!");
		
		return new BrasilPrevUserPrincipal(cliente);
	}
	
	public List<Cliente> getAllCustomers(){
		List<Cliente> lstCustomers = new ArrayList<Cliente>();
		Iterable<Cliente> customers = this.clienteRepository.findAll();
		
		customers.forEach(c -> {
			lstCustomers.add(c);
		});
		
		return lstCustomers;
	}
	
	public Cliente getCustomerById(Long id) {
		Cliente cliente = this.clienteRepository.findOne(id);
		return cliente;
	}
	public Cliente saveCustomer(Cliente cliente) {
		SecurityJavaConfig sec = new SecurityJavaConfig();
		cliente.setSenha(sec.encoder().encode(cliente.getSenha()));
		return this.clienteRepository.save(cliente);
	}
	
	public Boolean deleteCustomer(Cliente cliente) {
		this.clienteRepository.delete(cliente);
		return true;
	}
	
	
}
