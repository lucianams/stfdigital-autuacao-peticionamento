package br.jus.stf.autuacao.peticionamento.domain;

import br.jus.stf.autuacao.peticionamento.domain.model.Peticionador;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 01.07.2016
 */
@FunctionalInterface
public interface PeticionadorAdapter {
	
	/**
	 * @return
	 */
	Peticionador peticionador();

}
