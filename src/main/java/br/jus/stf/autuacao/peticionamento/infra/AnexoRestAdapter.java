package br.jus.stf.autuacao.peticionamento.infra;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.jus.stf.autuacao.peticionamento.domain.AnexoAdapter;
import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.DocumentoTemporarioId;

/**
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 05.05.2016
 */
@Component
public class AnexoRestAdapter implements AnexoAdapter {

	@Autowired
    private DiscoveryClient discoveryClient;
	
	@Override
	public List<DocumentoId> salvar(List<DocumentoTemporarioId> documentosTemporarios) {
		
		MultiValueMap<String, String> command = new LinkedMultiValueMap<String, String>();
		documentosTemporarios.forEach(doc -> command.add("idsDocumentosTemporarios", doc.toString()));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);      

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(command, headers);

		List<ServiceInstance> servicos = discoveryClient.getInstances("gateway"); 
		URI servicesUri = servicos.get(0).getUri();
		
		URI url = UriComponentsBuilder.fromUri(servicesUri).path("/documents/api/documentos").build().toUri();
		
		
		ParameterizedTypeReference<List<Map<String, Object>>> responseType = new ParameterizedTypeReference<List<Map<String, Object>>>() {};
		ResponseEntity<List<Map<String, Object>>> resp = new RestTemplate().exchange(url, HttpMethod.POST, request, responseType);
		
		//ResponseEntity<Map<String, Object>[]> resposta = new RestTemplate().postForEntity(url, request, Map<String, Object>[].class);
		
		return resp.getBody().stream().map(doc -> new DocumentoId(((Integer) doc.get("documentoId")).longValue())).collect(Collectors.toList());
	}

}
