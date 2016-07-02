package br.jus.stf.autuacao.peticionamento.infra.client;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.jus.stf.core.shared.identidade.PessoaId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 29.06.2016
 */
@FeignClient(name = "userauthentication")
@FunctionalInterface
public interface PessoaRestClient {

	@RequestMapping(method = RequestMethod.POST, value = "/api/pessoas/alocar")
	Set<PessoaId> identificadores(Map<String, List<String>> nomes);

}
