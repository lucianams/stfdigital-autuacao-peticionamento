package br.jus.stf.autuacao.peticionamento.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Representa um tipo de anexo.
 * 
 * @author Tomas.Godoi
 *
 */
@ApiModel("Representa um tipo de anexo")
public class TipoAnexoDto {

	@ApiModelProperty("O id do tipo de anexo")
	private Long id;
	
	@ApiModelProperty("O nome do tipo de anexo")
	private String nome;
	
	public TipoAnexoDto() {
		
	}

	public TipoAnexoDto(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
}
