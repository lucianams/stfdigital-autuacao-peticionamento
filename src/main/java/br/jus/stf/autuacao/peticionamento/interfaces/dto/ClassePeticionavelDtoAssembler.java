package br.jus.stf.autuacao.peticionamento.interfaces.dto;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.peticionamento.domain.model.classe.ClassePeticionavel;

/**
 * Classe usada para transformar uma entidade ClassePeticionavel em um objeto ClassePeticionavelDto.
 * 
 * @author anderson.araujo
 * @since 09/05/2016
 *
 */
@Component
public class ClassePeticionavelDtoAssembler {
	public ClassePeticionavelDto toDto(ClassePeticionavel classe) {
		Validate.notNull(classe);
		return new ClassePeticionavelDto(classe.identity().toString(), classe.nome());
	}
}
