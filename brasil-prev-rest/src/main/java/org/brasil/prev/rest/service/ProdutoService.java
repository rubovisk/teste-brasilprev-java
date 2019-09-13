package org.brasil.prev.rest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.brasil.prev.rest.domain.ProdutoFileType;
import org.brasil.prev.rest.entity.Categoria;
import org.brasil.prev.rest.entity.Cliente;
import org.brasil.prev.rest.entity.Produto;
import org.brasil.prev.rest.repository.ProdutoRepository;
import org.brasil.prev.rest.security.SecurityJavaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProdutoService {
	private final ProdutoRepository repository;
	
	@Autowired
	public ProdutoService(ProdutoRepository repository) {
		super();
		this.repository = repository;
	}
	
	public List<Produto> getAllProducts(){
		List<Produto> lstProdutos = new ArrayList<Produto>();
		Iterable<Produto> produtos = this.repository.findAll();
		
		produtos.forEach(prd -> {
			lstProdutos.add(prd);
		});
		
		return lstProdutos;
	}
	
	public Produto saveProduct(ProdutoFileType produto) throws IOException {
		Produto persist = new Produto();
		persist.setFoto(produto.getFile().getBytes());
		persist.setDescricao(produto.getDescricao());
		persist.setIdCategoria(produto.getIdCategoria());
		persist.setPreco(produto.getPreco());
		persist.setProduto(produto.getProduto());
		persist.setQuantidade(produto.getQuantidade());
		
		return this.repository.save(persist);
	}
	public Produto saveProduct(Produto produto) {
		return this.repository.save(produto);
	}
	
	public Produto  getProductById(Long id) {
		Produto produto = this.repository.findOne(id);
		return produto ;
	}
	
	public Boolean deleteProduct(Produto produto) {
		this.repository.delete(produto);
		return true;
	}
}
