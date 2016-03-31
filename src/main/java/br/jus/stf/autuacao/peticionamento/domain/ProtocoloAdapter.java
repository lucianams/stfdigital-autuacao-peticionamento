package br.jus.stf.autuacao.peticionamento.domain;

import org.springframework.stereotype.Component;

import br.jus.stf.core.shared.protocolo.Protocolo;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.02.2016
 */
@Component
@FunctionalInterface
public interface ProtocoloAdapter {

	public Protocolo novoProtocolo();
	
}
