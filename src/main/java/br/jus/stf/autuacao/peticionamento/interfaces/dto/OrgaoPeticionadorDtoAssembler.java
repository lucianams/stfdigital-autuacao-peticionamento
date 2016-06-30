/**
 * 
 */
package br.jus.stf.autuacao.peticionamento.interfaces.dto;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionador;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 29.06.2016
 */
@Component
public class OrgaoPeticionadorDtoAssembler {
	public OrgaoPeticionadorDto toDto(OrgaoPeticionador orgao){
		return new OrgaoPeticionadorDto(orgao.identity().toLong(), orgao.nome());
	}
}
