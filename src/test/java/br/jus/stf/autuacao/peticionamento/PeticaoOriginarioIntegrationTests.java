package br.jus.stf.autuacao.peticionamento;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import br.jus.stf.autuacao.peticionamento.domain.AnexoAdapter;
import br.jus.stf.autuacao.peticionamento.domain.EnvolvidoAdapter;
import br.jus.stf.autuacao.peticionamento.domain.ProtocoloAdapter;
import br.jus.stf.core.framework.testing.IntegrationTestsSupport;
import br.jus.stf.core.framework.testing.oauth2.WithMockOauth2User;
import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.DocumentoTemporarioId;
import br.jus.stf.core.shared.identidade.PessoaId;
import br.jus.stf.core.shared.protocolo.Numero;
import br.jus.stf.core.shared.protocolo.Protocolo;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * Valida a API de envio de petições.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.02.2016
 */
@SpringBootTest(value = {"server.port:0", "eureka.client.enabled:false"}, classes = ApplicationContextInitializer.class)
@WithMockOauth2User("peticionador")
public class PeticaoOriginarioIntegrationTests extends IntegrationTestsSupport {
	
	@MockBean
    private ProtocoloAdapter protocoloAdapter;
	
	@MockBean
	private AnexoAdapter anexoAdapter;
	
	@MockBean
    private EnvolvidoAdapter envolvidoAdapter;
	
	private String idDocTemp = "_DocTemp_";
	
	@Before
	public void configuracao() {
		given(protocoloAdapter.novoProtocolo()).willReturn(new Protocolo(new ProtocoloId(1L), new Numero(1L, 2016)));
		given(anexoAdapter.salvar(Arrays.asList(new DocumentoTemporarioId(idDocTemp)))).willReturn(Arrays.asList(new DocumentoId(1L)));
		given(envolvidoAdapter.pessoasEnvolvidas(Arrays.asList(anyString()))).willReturn(new HashSet<PessoaId>(Arrays.asList(new PessoaId(1L))));
	}

	@Test
    public void registrarUmaPeticao() throws Exception {
        String peticaoValida = "{\"classeId\":\"ADI\", \"preferencias\":[3,8], \"poloAtivo\": [\"Maria\", \"João\"], \"poloPassivo\": [\"Antônia\"], \"anexos\": [{\"documentoId\":\"@idDocTemp\", \"tipoDocumentoId\":1}], \"sigilo\":\"PUBLICO\", \"tipoProcesso\":\"ORIGINARIO\"}";
        ResultActions result = mockMvc.perform(post("/api/peticoes").contentType(APPLICATION_JSON).content(peticaoValida.replaceAll("@idDocTemp", idDocTemp)));
        
        result.andExpect(status().isOk());
    }
	
	@Test
    public void registrarUmaPeticaoComRepresentacao() throws Exception {
		loadDataTests("cadastrarAssociado-limpar.sql", "cadastrarAssociado.sql");
		
        String peticaoValida = "{\"classeId\":\"ADI\", \"orgaoId\":12452261, \"poloAtivo\": [\"Maria\"], \"poloPassivo\": [\"João\"], \"anexos\": [{\"documentoId\":\"@idDocTemp\", \"tipoDocumentoId\":1}], \"sigilo\":\"PUBLICO\", \"tipoProcesso\":\"ORIGINARIO\"}";
        ResultActions result = mockMvc.perform(post("/api/peticoes/representado").contentType(APPLICATION_JSON).content(peticaoValida.replaceAll("@idDocTemp", idDocTemp)));
        
        result.andExpect(status().isOk());
    }
	
	@Test
    public void naoDeveRegistrarUmaPeticaoInvalida() throws Exception {
        String peticaoInvalida = "{\"classeId\":\"\", \"envolvidos\": [{\"ativo\":[1, 2]}, {\"passivo\":[3, 4]}], \"anexos\": [{\"documento\":1, \"tipo\":1}]}";
        
        ResultActions result = mockMvc.perform(post("/api/peticoes").contentType(APPLICATION_JSON).content(peticaoInvalida));
        
        result.andExpect(status().isBadRequest());
    }
	
	@Test
    public void consultarPeticaoOriginaria() throws Exception {
		loadDataTests("peticaoOriginario-limpar.sql", "peticaoOriginario.sql");
		
    	mockMvc.perform(get("/api/peticoes/9002/envolvidos")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)));
    }
	
	@Test
	public void listarOrgaosPeticionadoresConformeAssociado() throws Exception {
		loadDataTests("cadastrarAssociado-limpar.sql", "cadastrarAssociado.sql");
		
		ResultActions result = mockMvc.perform(get("/api/peticoes/orgaos").param("verificarPerfil", "false"));
        
        result.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
	}
	
	@Test
	public void listarOrgaosPeticionadoresVerificandoPerfil() throws Exception {
		ResultActions result = mockMvc.perform(get("/api/peticoes/orgaos").param("verificarPerfil", "true"));
        
        result.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(35)));
	}
	
	@Test
	public void consultarSigilos() throws Exception {
		ResultActions result = mockMvc.perform(get("/api/peticoes/sigilos"));
        
        result.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
	}
    
}
