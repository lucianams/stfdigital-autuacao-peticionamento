package br.jus.stf.autuacao.peticionamento.interfaces.dto;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 28.04.2016
 */
public class EnvolvidoDto {
	
	private String apresentacao;
	
	private Long pessoa;
	
	public EnvolvidoDto() {
		
	}
	
	public EnvolvidoDto(String apresentacao, Long pessoa) {
		this.apresentacao = apresentacao;
		this.pessoa = pessoa;
	}
	
	public String getApresentacao() {
		return apresentacao;
	}
	
	public void setApresentacao(String apresentacao) {
		this.apresentacao = apresentacao;
	}
	
	public Long getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(Long pessoa) {
		this.pessoa = pessoa;
	}
	
}
