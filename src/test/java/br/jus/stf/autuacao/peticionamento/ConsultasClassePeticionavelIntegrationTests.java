package br.jus.stf.autuacao.peticionamento;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import  br.jus.stf.core.framework.testing.IntegrationTestsSupport;
import br.jus.stf.core.framework.testing.oauth2.WithMockOauth2User;

/**
 * Teste de integração do serviço REST de classes peticionáveis.
 * 
 * @author anderson.araujo
 * @since 09/05/2016
 *
 */
@SpringBootTest(value = {"server.port:0", "eureka.client.enabled:false", "spring.cloud.config.enabled:false"}, classes = ApplicationContextInitializer.class)
@WithMockOauth2User("peticionador")
public class ConsultasClassePeticionavelIntegrationTests extends IntegrationTestsSupport {
	
	@Test
	public void listarClassesPeticionaveis() throws Exception{
		mockMvc.perform(get("/api/classes")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(26)));
	}

}
