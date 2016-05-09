package br.jus.stf.autuacao.peticionamento.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * DTO usado para transportar dados de classe peticionável para o front-end.
 * 
 * @author anderson.araujo
 * @since 09/05/2014
 *
 */
@ApiModel(value = "DTO usado para transportar dados de classe peticionável para o front-end.")
public class ClassePeticionavelDto {
	@ApiModelProperty(value = "A sigla da classe. Por exemplo: 'AP'.")
	private String sigla;

	@ApiModelProperty(value = "O nome da classe. Por exemplo: 'Ação Penal'.")
	private String nome;
	
	public ClassePeticionavelDto(String sigla, String nome) {
		this.sigla = sigla;
		this.nome = nome;
	}
	
	public String getSigla() {
		return sigla;
	}
	
	public String getNome() {
		return nome;
	}
}
