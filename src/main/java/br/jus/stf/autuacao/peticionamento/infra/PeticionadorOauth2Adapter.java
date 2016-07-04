package br.jus.stf.autuacao.peticionamento.infra;

import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.peticionamento.domain.PeticionadorAdapter;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticionador;
import br.jus.stf.core.shared.identidade.PessoaId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 01.07.2016
 */
@Component
public class PeticionadorOauth2Adapter implements PeticionadorAdapter {

	@Override
	@SuppressWarnings("unchecked")
	public Peticionador peticionador() {
		OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
		
		Map<String, Object> principal = (Map<String, Object>) authentication.getUserAuthentication().getDetails();
		
		Long pessoaId = Long.valueOf(principal.get("pessoaId").toString());
		
		String login = principal.get("login").toString();
		
		return new Peticionador(login, new PessoaId(pessoaId));
	}

}
