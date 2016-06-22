package br.jus.stf.autuacao.peticionamento;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.joda.time.Duration;
import org.junit.Before;
import org.junit.ClassRule;
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

import com.palantir.docker.compose.DockerComposition;
import com.palantir.docker.compose.connection.Container;
import com.palantir.docker.compose.connection.DockerMachine;
import com.palantir.docker.compose.connection.waiting.HealthCheck;
import com.palantir.docker.compose.connection.waiting.SuccessOrFailure;

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
	
	@ClassRule
    public static DockerComposition composition = DockerComposition.of("src/test/resources/docker-compose.yml").
    	machine(DockerMachine.remoteMachine().host("tcp://192.168.99.100:2376").build())
    	.waitingForService("gateway", new CustomHealthCheck(), Duration.standardMinutes(5))
    	.skipShutdown(true).build();
	
	public static class CustomHealthCheck implements HealthCheck<Container> {

		static {
			try {
				String keystoreType = "JKS";
				InputStream keystoreLocation = null;
				char[] keystorePassword = null;
				char[] keyPassword = null;

				KeyStore keystore = KeyStore.getInstance(keystoreType);
				keystore.load(keystoreLocation, keystorePassword);
				KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
				kmfactory.init(keystore, keyPassword);

				InputStream truststoreLocation = new FileInputStream("truststores/truststore.jks");
				char[] truststorePassword = "changeit".toCharArray();
				String truststoreType = "JKS";

				KeyStore truststore = KeyStore.getInstance(truststoreType);
				truststore.load(truststoreLocation, truststorePassword);
				TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
				tmfactory.init(truststore);

				KeyManager[] keymanagers = kmfactory.getKeyManagers();
				TrustManager[] trustmanagers = tmfactory.getTrustManagers();

				SSLContext sslContext = SSLContext.getInstance("TLS");
				sslContext.init(keymanagers, trustmanagers, new SecureRandom());
				SSLContext.setDefault(sslContext);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		@Override
		public SuccessOrFailure isHealthy(Container container) {
			SuccessOrFailure response = container.portIsListeningOnHttp(8765, (port) -> port.inFormat("https://$HOST:$EXTERNAL_PORT/documents/info"));
			if (response.succeeded() && isHttpResponding("https://docker:" + container.portMappedInternallyTo(8765).getExternalPort() + "/documents/info")) {
				return SuccessOrFailure.success();
			} else {
				return SuccessOrFailure.failure("Erro");
			}
		}
		
	    public boolean isHttpResponding(String urlString) {
	        URL url;
	        try {
	            url = new URL(urlString);
	        } catch (MalformedURLException e) {
	            throw new RuntimeException("Could not create URL for connecting to localhost", e);
	        }
	        try {
	        	HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
	        	connection.setRequestMethod("GET");
	        	connection.connect();
	        	int code = connection.getResponseCode();
	        	return code == 200;
	        } catch (SocketException e) {
	            return false;
	        } catch (FileNotFoundException e) {
	            return false;
	        } catch (SSLHandshakeException e) {
	            return false;
	        } catch (IOException e) {
	            return false;
	        }
	    }
		
	}
	
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
    public void consultarPeticao() throws Exception {
    	mockMvc.perform(get("/api/peticoes/99999/envolvidos")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }
    
}
