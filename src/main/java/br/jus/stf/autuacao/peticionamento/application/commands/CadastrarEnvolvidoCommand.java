package br.jus.stf.autuacao.peticionamento.application.commands;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 07.06.2016
 */
public class CadastrarEnvolvidoCommand {
	
	private String apresentacao;
	
	private Long pessoa;
	
	public CadastrarEnvolvidoCommand() {
		
	}
	
	public CadastrarEnvolvidoCommand(String apresentacao, Long pessoa) {
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
