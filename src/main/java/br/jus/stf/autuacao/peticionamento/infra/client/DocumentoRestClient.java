package br.jus.stf.autuacao.peticionamento.infra.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 01.07.2016
 */
@FeignClient(name = "documents")
@FunctionalInterface
public interface DocumentoRestClient {

	@RequestMapping(method = RequestMethod.POST, value = "/api/documentos", consumes = "application/json")
	List<Map<String, Object>> documentos(MultiValueMap<String, String> documents);

}
