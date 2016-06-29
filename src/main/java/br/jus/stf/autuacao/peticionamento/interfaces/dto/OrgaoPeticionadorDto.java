package br.jus.stf.autuacao.peticionamento.interfaces.dto;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 29.06.2016
 */
public class OrgaoPeticionadorDto {
	
	private Long id;
	
	private String nome;
	
	public OrgaoPeticionadorDto() {
		
	}
	
	public OrgaoPeticionadorDto(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
