package org.brasil.prev.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.brasil.prev.rest.entity.Categoria;
import org.brasil.prev.rest.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	public CategoriaService(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}
	
	public List<Categoria> getAllCategories(){
		List<Categoria> lstCategorias = new ArrayList<Categoria>();
		Iterable<Categoria> categories = this.categoriaRepository.findAll();
		
		categories.forEach(cat -> {
			lstCategorias.add(cat);
		});
		
		return lstCategorias;
	}
	
	public Categoria getCategorieById(Long id) {
		Categoria categoria = this.categoriaRepository.findOne(id);
		return categoria;
	}
	
	public Categoria saveCategoria(Categoria categoria) {
		return this.categoriaRepository.save(categoria);
	}
	
	public Boolean deleteCategoria(Categoria categoria) {
		this.categoriaRepository.delete(categoria);
		return true;
	}
}
