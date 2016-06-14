package br.jus.stf.autuacao.peticionamento.infra;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.jus.stf.autuacao.peticionamento.domain.EnvolvidoAdapter;
import br.jus.stf.core.shared.identidade.PessoaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 08.06.2016
 */
@Component
public class EnvolvidoRestAdapter implements EnvolvidoAdapter {
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Override
	public Set<PessoaId> pessoasEnvolvidas(List<String> nomes) {
		return discoveryClient.getInstances("gateway").stream().findAny().map(instance -> {
			URI servicesUri = instance.getUri();
			Map<String, List<String>> params = new HashMap<>();
			
			params.put("nomes", nomes);
			
			URI uri = UriComponentsBuilder.fromUri(servicesUri).path("/userauthentication/api/pessoas/alocar").build().toUri();
			Set<Long> pessoasId = new HashSet<>(Arrays.asList(new RestTemplate().postForObject(uri, params, Long[].class)));
			
			return pessoasId.stream().map(pessoaId -> new PessoaId(pessoaId)).collect(Collectors.toSet());
		}).orElse(new HashSet<>(0));
	}
	
}