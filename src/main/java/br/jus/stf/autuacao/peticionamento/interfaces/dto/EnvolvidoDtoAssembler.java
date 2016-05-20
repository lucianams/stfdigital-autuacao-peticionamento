/**
 * 
 */
package br.jus.stf.autuacao.peticionamento.interfaces.dto;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.peticionamento.domain.model.Envolvido;

/**
 * Cria objetos EnvolvidoDto.
 * 
 * @author anderson.araujo
 * @since 20/05/2016
 *
 */
@Component
public class EnvolvidoDtoAssembler {
	public EnvolvidoDto toDto(Envolvido envolvido){
		return new EnvolvidoDto(envolvido.apresentacao(), Optional.ofNullable(envolvido.pessoa()).isPresent() ? envolvido.pessoa().toLong() : 1L);
	}
}
