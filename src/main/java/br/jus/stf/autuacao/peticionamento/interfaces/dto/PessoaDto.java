package br.jus.stf.autuacao.peticionamento.interfaces.dto;

/**
 * Usado para receber os dados orindos do contexto de identidade (pessoas).
 * 
 * @author anderson.araujo
 * @since 31/05/2016
 *
 */
public class PessoaDto {
	private Long id;
	private String nome;
	
	public PessoaDto() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	public PessoaDto(Long id, String nome){
		this.id = id;
		this.nome = nome;
	}
	
	public Long getId(){
		return id;
	}
	
	public String getNome(){
		return nome;
	}
}
