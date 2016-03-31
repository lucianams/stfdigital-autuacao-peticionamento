package br.jus.stf.autuacao.peticionamento.domain.model;

import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.01.2016
 */
public interface PeticaoRepository {

	<P extends Peticao> P save(P peticao);
	
	Peticao findOne(ProtocoloId id);

}
