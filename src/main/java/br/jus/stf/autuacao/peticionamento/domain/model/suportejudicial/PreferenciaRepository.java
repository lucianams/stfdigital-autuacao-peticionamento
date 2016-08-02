package br.jus.stf.autuacao.peticionamento.domain.model.suportejudicial;

import br.jus.stf.core.shared.preferencia.PreferenciaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 06.04.2016
 */
@FunctionalInterface
public interface PreferenciaRepository {

	/**
	 * @param id
	 * @return
	 */
	Preferencia findOne(PreferenciaId id);

}
