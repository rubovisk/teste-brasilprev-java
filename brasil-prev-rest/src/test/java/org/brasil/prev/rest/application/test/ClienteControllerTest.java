package org.brasil.prev.rest.application.test;

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
import java.util.ArrayList;
import java.util.List;

import org.brasil.prev.rest.entity.Cliente;
import org.brasil.prev.rest.service.ClienteService;
import org.hamcrest.text.IsEmptyString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class ClienteControllerTest {
	private final String allCustomersPath = "/customers/all";
	private final String findCustomersPath = "/customers/";
	private final String createCustomersPath = "/customers";
	private final String id = "1";
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
	
	@MockBean
	private ClienteService service;
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornarTodosClientes() throws Exception {
		List<Cliente> lstCategorias = new ArrayList<Cliente>();

		given(service.getAllCustomers()).willReturn(lstCategorias);

		mvc.perform(get(base.toString() + allCustomersPath).contentType(APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornarUmClientePorID() throws Exception {
		given(service.getCustomerById(Mockito.anyLong())).willReturn(new Cliente());

		mvc.perform(get(base.toString() + findCustomersPath + id).contentType(APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornarNuloAoBuscarUmClientePorID() throws Exception {
		final String wrongId = "abc";

		mvc.perform(get(base.toString() + findCustomersPath + wrongId).contentType(APPLICATION_JSON)).andExpect(status().isOk()).
		andExpect(content().string(IsEmptyString.isEmptyOrNullString()));
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornar200AoCriarNovoCliente() throws Exception {
		given(service.saveCustomer(Mockito.any(Cliente.class))).willReturn(new Cliente());

		mvc.perform(post(base.toString() + createCustomersPath).contentType(APPLICATION_JSON).content(createNewCustomer())).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornar204AoAtualizarCliente() throws Exception {
		given(service.getCustomerById(Mockito.anyLong())).willReturn(null);

		mvc.perform(put(base.toString() + findCustomersPath + id).contentType(APPLICATION_JSON).content(updateCategorie())).andExpect(status().isNoContent());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornar200AoAtualizarCliente() throws Exception {
		given(service.getCustomerById(Mockito.anyLong())).willReturn(new Cliente());
		given(service.saveCustomer(Mockito.any(Cliente.class))).willReturn(new Cliente());

		mvc.perform(put(base.toString() + findCustomersPath + id).contentType(APPLICATION_JSON).content(updateCategorie())).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornar204AoDeletarCliente() throws Exception {
		given(service.getCustomerById(Mockito.anyLong())).willReturn(null);

		mvc.perform(put(base.toString() + findCustomersPath + id).contentType(APPLICATION_JSON).content(updateCategorie())).andExpect(status().isNoContent());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornarFalseAoDeletarCliente() throws Exception {
		given(service.getCustomerById(Mockito.anyLong())).willReturn(null);

		mvc.perform(delete(base.toString() + findCustomersPath + id).contentType(APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(value = "rdmello")
	public void deveRetornarTrueAoDeletarCliente() throws Exception {
		given(service.getCustomerById(Mockito.anyLong())).willReturn(new Cliente());
		given(service.deleteCustomer(Mockito.any(Cliente.class))).willReturn(Boolean.TRUE);

		mvc.perform(delete(base.toString() + findCustomersPath + id).contentType(APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	private String createNewCustomer()
	{
		return "{\"bairro\": \"bairro teste\",\"cep\": \"000555667\",\"cidade\": \"sao paulo\",\"email\": \"teste@teste.com\",\"estado\": \"SP\",\"rua\": \"rua teste\",\"senha\": \"teste123\",\"userName\": \"rubens\"}";
	}
	private String updateCategorie()
	{
		return "{\"bairro\": \"bairro teste1\",\"cep\": \"000555667\",\"cidade\": \"sao paulo\",\"email\": \"teste1@teste.com\",\"estado\": \"SP\",\"rua\": \"rua teste\",\"senha\": \"teste1234\",\"userName\": \"rubens\"}";
	}
}
