package org.brasil.prev.rest.application.test;

import static java.util.Collections.singletonList;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.brasil.prev.rest.application.CategoriaController;
import org.brasil.prev.rest.entity.Categoria;
import org.brasil.prev.rest.service.CategoriaService;
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
public class CategoriaControllerTest {
	private final String allCategoriesPath = "/categories/all";
	private final String findCategoriesPath = "/categories/";
	private final String createCategoriesPath = "/categories";
	URL base;
	private MockMvc mvc;

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
	private CategoriaController categoriaController;
	
	@MockBean
	private CategoriaService service;

	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornarTodasCategorias() throws Exception {
		Categoria c = new Categoria();
		c.setCategoria("Seguros");
		c.setIdCategoria(1L);

		List<Categoria> lstCategorias = singletonList(c);

		given(service.getAllCategories()).willReturn(lstCategorias);

		mvc.perform(get(base.toString() + allCategoriesPath).contentType(APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornarUmaCategoriaPorID() throws Exception {
		final String id = "1";
		given(service.getCategorieById(Mockito.anyLong())).willReturn(new Categoria());

		mvc.perform(get(base.toString() + findCategoriesPath + id).contentType(APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornarNuloAoBuscarUmaCategoriaPorID() throws Exception {
		final String wrongId = "abc";

		mvc.perform(get(base.toString() + findCategoriesPath + wrongId).contentType(APPLICATION_JSON)).andExpect(status().isOk()).
		andExpect(content().string(IsEmptyString.isEmptyOrNullString()));
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornar200AoCriarNovaCategoria() throws Exception {
		given(service.saveCategoria(Mockito.any(Categoria.class))).willReturn(new Categoria());

		mvc.perform(post(base.toString() + createCategoriesPath).contentType(APPLICATION_JSON).content(createNewCategorie())).andExpect(status().isOk());
	}
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornar204AoAtualizarCategoria() throws Exception {
		final String id = "1";
		given(service.getCategorieById(Mockito.anyLong())).willReturn(null);

		mvc.perform(put(base.toString() + findCategoriesPath + id).contentType(APPLICATION_JSON).content(updateCategorie())).andExpect(status().isNoContent());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornar200AoAtualizarCategoria() throws Exception {
		final String id = "1";
		given(service.getCategorieById(Mockito.anyLong())).willReturn(new Categoria());
		given(service.saveCategoria(Mockito.any(Categoria.class))).willReturn(new Categoria());

		mvc.perform(put(base.toString() + findCategoriesPath + id).contentType(APPLICATION_JSON).content(updateCategorie())).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornar204AoDeletarCategoria() throws Exception {
		final String id = "1";
		given(service.getCategorieById(Mockito.anyLong())).willReturn(null);

		mvc.perform(put(base.toString() + findCategoriesPath + id).contentType(APPLICATION_JSON).content(updateCategorie())).andExpect(status().isNoContent());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornarFalseAoDeletarCategoria() throws Exception {
		final String id = "1";
		given(service.getCategorieById(Mockito.anyLong())).willReturn(null);

		mvc.perform(delete(base.toString() + findCategoriesPath + id).contentType(APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornarTrueAoDeletarCategoria() throws Exception {
		final String id = "1";
		given(service.getCategorieById(Mockito.anyLong())).willReturn(new Categoria());
		given(service.deleteCategoria(Mockito.any(Categoria.class))).willReturn(Boolean.TRUE);

		mvc.perform(delete(base.toString() + findCategoriesPath + id).contentType(APPLICATION_JSON)).andExpect(status().isOk());
	}
	private String createNewCategorie()
	{
		return "{\"categoria\": \"seguro\",\"idCategoria\": \"3\"}";
	}
	private String updateCategorie()
	{
		return "{\"categoria\": \"seguro123\"}";
	}
}
