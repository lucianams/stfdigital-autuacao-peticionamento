package br.jus.stf.autuacao.peticionamento;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import br.jus.stf.core.framework.testing.IntegrationTestsSupport;

/**
 * Valida a API de envio de petições.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.02.2016
 */
@Ignore
@SpringApplicationConfiguration(ApplicationContextInitializer.class)
public class PeticaoIntegrationTests extends IntegrationTestsSupport {
    
	@Test
    public void registrarUmaPeticao() throws Exception {
        String peticaoValida = "{\"classeId\":\"ADI\", \"preferencias\":[3,8], \"poloAtivo\": [{\"apresentacao\":\"Maria\",\"pessoa\":1}, {\"apresentacao\":\"João\"}], \"poloPassivo\": [{\"apresentacao\":\"Antônia\",\"pessoa\":3}], \"anexos\": [{\"documento\":1, \"tipo\":1}]}";
        
        ResultActions result = mockMvc.perform(post("/api/peticoes").contentType(APPLICATION_JSON).content(peticaoValida));
        
        result.andExpect(status().isOk());
    }
	
	@Test
    public void registrarUmaPeticaoComRepresentacao() throws Exception {
        String peticaoValida = "{\"classeId\":\"ADI\", \"orgaoId\":12452261, \"poloAtivo\": [{\"apresentacao\":\"Maria\"}], \"poloPassivo\": [{\"apresentacao\":\"João\", \"pessoa\":3}], \"anexos\": [{\"documento\":1, \"tipo\":1}]}";
        
        ResultActions result = mockMvc.perform(post("/api/peticoes").contentType(APPLICATION_JSON).content(peticaoValida));
        
        result.andExpect(status().isOk());
    }
	
    @Test
    public void naoDeveRegistrarUmaPeticaoInvalida() throws Exception {
        String peticaoInvalida = "{\"classeId\":\"\", \"envolvidos\": [{\"ativo\":[1, 2]}, {\"passivo\":[3, 4]}], \"anexos\": [{\"documento\":1, \"tipo\":1}]}";
        
        ResultActions result = mockMvc.perform(post("/api/peticoes").contentType(APPLICATION_JSON).content(peticaoInvalida));
        
        result.andExpect(status().isBadRequest());
    }
    
    public void consultarPeticao() throws Exception {
    	mockMvc.perform(get("/api/peticoes/99999/envolvidos")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }
}
