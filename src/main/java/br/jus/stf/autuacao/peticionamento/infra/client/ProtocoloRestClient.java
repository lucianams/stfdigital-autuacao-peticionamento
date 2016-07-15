package br.jus.stf.autuacao.peticionamento.infra.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 01.07.2016
 */
@FeignClient(name = "services")
public interface ProtocoloRestClient {

	@RequestMapping(method = RequestMethod.GET, value = "/api/identificadores")
	Long identificador(@RequestParam(value = "categoria") String categoria);

	@RequestMapping(method = RequestMethod.GET, value = "/api/identificadores")
	Long identificador();
}
