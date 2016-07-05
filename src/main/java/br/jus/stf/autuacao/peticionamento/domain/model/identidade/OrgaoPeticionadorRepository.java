package br.jus.stf.autuacao.peticionamento.domain.model.identidade;

import java.util.List;

import br.jus.stf.core.shared.identidade.PessoaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 08.04.2016
 */
public interface OrgaoPeticionadorRepository {

	/**
	 * @return
	 */
	List<OrgaoPeticionador> findAll();
	
	/**
	 * @param id
	 * @return
	 */
	OrgaoPeticionador findOne(PessoaId id);
	
	/**
	 * @param verificarPerfil
	 * @return
	 */
	List<OrgaoPeticionador> findOrgaoRepresentados(boolean verificarPerfil);

}
