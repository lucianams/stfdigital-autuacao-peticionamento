package br.jus.stf.autuacao.peticionamento.infra;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

import br.jus.stf.autuacao.peticionamento.domain.EnvolvidoAdapter;
import br.jus.stf.autuacao.peticionamento.infra.client.PessoaRestClient;
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
	private PessoaRestClient pessoaRestClient;
	
	@Override
	public Set<PessoaId> pessoasEnvolvidas(List<String> nomes) {
		return pessoaRestClient.identificadores(ImmutableMap.of("nomes", nomes));
	}
	
}