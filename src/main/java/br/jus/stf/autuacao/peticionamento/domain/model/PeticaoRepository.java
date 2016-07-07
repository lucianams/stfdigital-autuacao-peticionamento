package br.jus.stf.autuacao.peticionamento.domain.model;

import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.01.2016
 */
public interface PeticaoRepository {

	/**
	 * @param peticao
	 * @return
	 */
	<P extends Peticao> P save(P peticao);
	
	/**
	 * @param id
	 * @return
	 */
	Peticao findOne(ProtocoloId id);

}
