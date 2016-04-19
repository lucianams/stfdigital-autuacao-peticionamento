package br.jus.stf.autuacao.peticionamento.domain.model.support;

import java.util.List;

import br.jus.stf.core.shared.identidade.PessoaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 08.04.2016
 */
public interface OrgaoPeticionadorRepository {

	List<OrgaoPeticionador> findAll();
	
	OrgaoPeticionador findOne(PessoaId id);

}
