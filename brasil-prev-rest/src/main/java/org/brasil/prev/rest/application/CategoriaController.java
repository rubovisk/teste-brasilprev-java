package org.brasil.prev.rest.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.brasil.prev.rest.entity.Categoria;
import org.brasil.prev.rest.service.CategoriaService;
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
@RequestMapping("/categories")
public class CategoriaController {
	
	@Autowired
	private CategoriaService service;
	
	@GetMapping(path="/all")
	public @ResponseBody List<Categoria> getAllCategories() {
		return service.getAllCategories();
	}
	
	@GetMapping(path="/{id}")
	public @ResponseBody Categoria getCategorieById(@PathVariable String id) {
		try {
			return service.getCategorieById(Long.parseLong(id));
		} catch(NumberFormatException ex) {
			return null;
		}
	}
	
	@PostMapping
	public Categoria addCategorie(@Valid @RequestBody Categoria categoria) {
		return service.saveCategoria(categoria);
	}
	
	@PutMapping(path="/{id}")
	public ResponseEntity<Categoria> updateCategorie(@PathVariable Long id, @Valid @RequestBody Categoria categoria){
		Categoria c = service.getCategorieById(id);
		
		if(c == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		c.setCategoria(categoria.getCategoria());
		
		return ResponseEntity.ok(service.saveCategoria(c));
	}
	
	@DeleteMapping(path="/{id}")
	public Map<String, Boolean> deleteCategorie(@PathVariable Long id) {
		Map<String, Boolean> response = new HashMap<>();
		Categoria c = service.getCategorieById(id);
		
		if(c == null) {
			response.put("excluido", Boolean.FALSE);
			return response;
		}
		service.deleteCategoria(c);
		
	
		response.put("excluido", Boolean.TRUE);
		return response;
	}
}
