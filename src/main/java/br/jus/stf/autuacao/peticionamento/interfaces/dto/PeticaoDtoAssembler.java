package br.jus.stf.autuacao.peticionamento.interfaces.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.peticionamento.domain.model.Envolvido;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticao;

/**
 * @author Rafael Alencar
 *
 */
@Component
public class PeticaoDtoAssembler {
	
	@Autowired
	private EnvolvidoDtoAssembler envolvidoDtoAssembler;
	
	/**
	 * @param peticao
	 * @return
	 */
	public PeticaoDto toDto(Peticao peticao) {
		Validate.notNull(peticao);
		
		return new PeticaoDto(peticao.identity().toLong(), peticao.classe().toString(), toEnvolvidoDto(peticao.envolvidos()), peticao.sigilo().name(), peticao.numero().numero() + "/" + peticao.numero().ano().toString());
	}
	
	private List<EnvolvidoDto> toEnvolvidoDto(Set<Envolvido> envolvidos) {
		return envolvidos.stream()
				.map(envolvidoDtoAssembler::toDto)
				.collect(Collectors.toList());
	}
}
