package org.brasil.prev.rest.application.test;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.brasil.prev.rest.application.ProdutoController;
import org.brasil.prev.rest.entity.Produto;
import org.brasil.prev.rest.service.ProdutoService;
import org.hamcrest.text.IsEmptyString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProdutoControllerTest {
	private final String allProductsPath = "/products/all";
	private final String findProductsPath = "/products/";
	private final String createProductsPath = "/products";
	URL base;
	private MockMvc mvc;
	private final String id = "1";
	
	@Value("${local.server.port}")
	Integer port;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() throws MalformedURLException {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		base = new URL("http://localhost:" + port);
	}

	@Mock
	private ProdutoController produtoController;
	
	@MockBean
	private ProdutoService service;
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornarTodosProdutos() throws Exception {
		List<Produto> lstProducts = new ArrayList<>();

		given(service.getAllProducts()).willReturn(lstProducts);

		mvc.perform(get(base.toString() + allProductsPath).contentType(APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornarUmProdutoPorID() throws Exception {
		given(service.getProductById(Mockito.anyLong())).willReturn(new Produto());

		mvc.perform(get(base.toString() + findProductsPath + id).contentType(APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornarNuloAoBuscarUmProdutoPorID() throws Exception {
		final String wrongId = "abc";

		mvc.perform(get(base.toString() + findProductsPath + wrongId).contentType(APPLICATION_JSON)).andExpect(status().isOk()).
		andExpect(content().string(IsEmptyString.isEmptyOrNullString()));
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornar200AoCriarNovoProduto() throws Exception {
		given(service.saveProduct(Mockito.any(Produto.class))).willReturn(new Produto());

		mvc.perform(post(base.toString() + createProductsPath).contentType(MULTIPART_FORM_DATA).content(createNewProduct())).andExpect(status().isOk());
	}
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornar204AoAtualizarProduto() throws Exception {
		given(service.getProductById(Mockito.anyLong())).willReturn(null);

		mvc.perform(put(base.toString() + findProductsPath + id).contentType(APPLICATION_JSON).content(updateProduct())).andExpect(status().isNoContent());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornar200AoAtualizarProduto() throws Exception {
		final String id = "1";
		given(service.getProductById(Mockito.anyLong())).willReturn(new Produto());
		given(service.saveProduct(Mockito.any(Produto.class))).willReturn(new Produto());

		mvc.perform(put(base.toString() + findProductsPath + id).contentType(APPLICATION_JSON).content(updateProduct())).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornar204AoDeletarProduto() throws Exception {
		given(service.getProductById(Mockito.anyLong())).willReturn(null);

		mvc.perform(put(base.toString() + findProductsPath + id).contentType(APPLICATION_JSON).content(updateProduct())).andExpect(status().isNoContent());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornarFalseAoDeletarProduto() throws Exception {
		given(service.getProductById(Mockito.anyLong())).willReturn(null);

		mvc.perform(delete(base.toString() + findProductsPath + id).contentType(APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornarTrueAoDeletarProduto() throws Exception {
		given(service.getProductById(Mockito.anyLong())).willReturn(new Produto());
		given(service.deleteProduct(Mockito.any(Produto.class))).willReturn(Boolean.TRUE);

		mvc.perform(delete(base.toString() + findProductsPath + id).contentType(APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	private String createNewProduct()
	{
		return "{\"descricao\": \"teste brasilprev\",\"foto\": \"\",\"idCategoria\": 1,\"preco\": 21,\"produto\": \"brasilprev\",\"quantidade\": 1}";
	}
	private String updateProduct()
	{
		return "{\"descricao\": \"teste brasilprev123\",\"foto\": \"\",\"idCategoria\": 1,\"preco\": 22,\"produto\": \"brasilprev123\",\"quantidade\": 3}";
	}

}
