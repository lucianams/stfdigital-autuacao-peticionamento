package br.jus.stf.autuacao.peticionamento.interfaces.dto;

import java.util.List;

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
	
	@ApiModelProperty(value = "As preferências possíveis para a classe.")
	private List<PreferenciaDto> preferencias;
	
	public ClassePeticionavelDto(String sigla, String nome, List<PreferenciaDto> preferencias) {
		this.sigla = sigla;
		this.nome = nome;
		this.preferencias = preferencias;
	}
	
	public String getSigla() {
		return sigla;
	}
	
	public String getNome() {
		return nome;
	}

	public List<PreferenciaDto> getPreferencias() {
		return preferencias;
	}
}
