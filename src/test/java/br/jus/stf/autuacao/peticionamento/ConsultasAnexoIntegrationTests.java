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
 * Teste de integração do serviço REST de anexos.
 * 
 * @author Rafael Alencar
 * @since 01.08.2016
 *
 */
@SpringBootTest(value = {"server.port:0", "eureka.client.enabled:false"}, classes = ApplicationContextInitializer.class)
@WithMockOauth2User("peticionador")
public class ConsultasAnexoIntegrationTests extends IntegrationTestsSupport {
	
	@Test
	public void listarAnexos() throws Exception{
		mockMvc.perform(get("/api/anexos/tipos")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(84)));
	}

}
