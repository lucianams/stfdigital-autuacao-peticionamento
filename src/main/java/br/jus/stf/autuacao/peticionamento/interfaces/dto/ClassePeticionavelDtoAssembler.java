package br.jus.stf.autuacao.peticionamento.interfaces.dto;

import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.peticionamento.domain.model.suportejudicial.ClassePeticionavel;

/**
 * Classe usada para transformar uma entidade ClassePeticionavel em um objeto ClassePeticionavelDto.
 * 
 * @author anderson.araujo
 * @since 09/05/2016
 *
 */
@Component
public class ClassePeticionavelDtoAssembler {
	
	@Autowired
	private PreferenciaDtoAssembler preferenciaDtoAssembler;
	
	public ClassePeticionavelDto toDto(ClassePeticionavel classe) {
		Validate.notNull(classe);
		return new ClassePeticionavelDto(classe.identity().toString(), classe.nome(), classe.preferencias().stream()
    			.map(preferencia -> preferenciaDtoAssembler.toDto(preferencia)).collect(Collectors.toList()));
	}
}
