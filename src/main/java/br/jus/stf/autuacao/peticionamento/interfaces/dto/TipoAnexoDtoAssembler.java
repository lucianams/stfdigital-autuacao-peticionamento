package br.jus.stf.autuacao.peticionamento.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.peticionamento.domain.model.documento.TipoAnexo;

/**
 * Conversor do TipoAnexo para TipoAnexoDto
 * 
 * @author Tomas.Godoi
 *
 */
@Component
public class TipoAnexoDtoAssembler {

	public TipoAnexoDto toDto(TipoAnexo tipoAnexo) {
		return new TipoAnexoDto(tipoAnexo.identity().toLong(), tipoAnexo.nome());
	}
	
}
