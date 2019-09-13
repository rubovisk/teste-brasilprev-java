package org.brasil.prev.rest.application;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.brasil.prev.rest.domain.ProdutoFileType;
import org.brasil.prev.rest.entity.Cliente;
import org.brasil.prev.rest.entity.Produto;
import org.brasil.prev.rest.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/products")
public class ProdutoController {
	@Autowired
	private ProdutoService service;

	@GetMapping(path = "/all")
	public @ResponseBody List<Produto> getAllProducts() {
		return service.getAllProducts();
	}
	
	@GetMapping(path="/{id}")
	public @ResponseBody Produto getProductById(@PathVariable String id) {
		try {
			return service.getProductById(Long.parseLong(id));
		} catch(NumberFormatException ex) {
			return null;
		}
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Produto addProduct(MultipartFile file, ProdutoFileType produto) throws IOException {
		return service.saveProduct(produto);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<Produto> updateProduct(@PathVariable Long id, @Valid @RequestBody Produto produto) {
		Produto p = service.getProductById(id);

		if (p == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}

		p.setDescricao(produto.getDescricao());
		p.setIdCategoria(produto.getIdCategoria());
		p.setPreco(produto.getPreco());
		p.setProduto(produto.getProduto());
		p.setQuantidade(produto.getQuantidade());

		return ResponseEntity.ok(service.saveProduct(p));

	}
	
	@DeleteMapping(path="/{id}")
	public Map<String, Boolean> deleteProduct(@PathVariable Long id) {
		Map<String, Boolean> response = new HashMap<>();
		Produto p = service.getProductById(id);
		
		if(p == null) {
			response.put("excluido", Boolean.FALSE);
			return response;
		}
		service.deleteProduct(p);
		
	
		response.put("excluido", Boolean.TRUE);
		return response;
	}
}
