package br.jus.stf.autuacao.peticionamento;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
@SpringApplicationConfiguration(ApplicationContextInitializer.class)
public class PeticaoIntegrationTests extends IntegrationTestsSupport {
    
    @Test
    public void naoDeveRegistrarUmaPeticaoInvalida() throws Exception {
        String peticao = "{\"classeId\":\"\", \"envolvidos\": [{\"ativo\":[1, 2]}, {\"passivo\":[3, 4]}], \"anexos\": [{\"documento\":1, \"tipo\":1}]}";
        
        ResultActions result = mockMvc.perform(post("/api/peticoes").contentType(APPLICATION_JSON).content(peticao));
        
        result.andExpect(status().isBadRequest());
    }
    
}
