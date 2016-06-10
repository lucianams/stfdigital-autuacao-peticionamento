package br.jus.stf.autuacao.peticionamento;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

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
	
	private String idDocTemp;
	
	@Before
	public void geraDocumentoTemporarioId() {
		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		HttpHeaders headers = new HttpHeaders();

		map.add("file", new ClassPathResource("certification/pdf-de-teste-assinado-02.pdf"));
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
				map, headers);
		ResponseEntity<String> result = new RestTemplate().exchange(
				"https://docker:8765/documents/api/documentos/upload/assinado", HttpMethod.POST, requestEntity,
				String.class);

		idDocTemp = result.getBody();
	}
	
	@Test
    public void registrarUmaPeticao() throws Exception {
        String peticaoValida = "{\"classeId\":\"ADI\", \"preferencias\":[3,8], \"poloAtivo\": [\"Maria\", \"João\"], \"poloPassivo\": [\"Antônia\"], \"anexos\": [{\"documentoId\":\"@idDocTemp\", \"tipoDocumentoId\":1}], \"sigilo\":\"PUBLICO\"}";
        ResultActions result = mockMvc.perform(post("/api/peticoes").contentType(APPLICATION_JSON).content(peticaoValida.replaceAll("@idDocTemp", idDocTemp)));
        
        result.andExpect(status().isOk());
    }

	@Test
    public void registrarUmaPeticaoComRepresentacao() throws Exception {
        String peticaoValida = "{\"classeId\":\"ADI\", \"orgaoId\":12452261, \"poloAtivo\": [\"Maria\"], \"poloPassivo\": [\"João\"], \"anexos\": [{\"documentoId\":\"@idDocTemp\", \"tipoDocumentoId\":1}], \"sigilo\":\"PUBLICO\"}";
        ResultActions result = mockMvc.perform(post("/api/peticoes").contentType(APPLICATION_JSON).content(peticaoValida.replaceAll("@idDocTemp", idDocTemp)));
        
        result.andExpect(status().isOk());
    }
	
	@Test
    public void naoDeveRegistrarUmaPeticaoInvalida() throws Exception {
        String peticaoInvalida = "{\"classeId\":\"\", \"envolvidos\": [{\"ativo\":[1, 2]}, {\"passivo\":[3, 4]}], \"anexos\": [{\"documento\":1, \"tipo\":1}]}";
        
        ResultActions result = mockMvc.perform(post("/api/peticoes").contentType(APPLICATION_JSON).content(peticaoInvalida));
        
        result.andExpect(status().isBadRequest());
    }
	
	@Test
    public void consultarPeticao() throws Exception {
    	mockMvc.perform(get("/api/peticoes/99999/envolvidos")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }
    
}
