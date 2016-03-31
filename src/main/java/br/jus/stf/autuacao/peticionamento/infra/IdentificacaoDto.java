package br.jus.stf.autuacao.peticionamento.infra;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 25.03.2016
 */
public class IdentificacaoDto {
	
	private String categoria;
	
	private Long numero;
	
	public IdentificacaoDto() {
    	// Usado pelo Jackson durante a conversação de JSon para uma nova instância.
	}

	public IdentificacaoDto(String categoria, Long numero) {
		this.categoria = categoria;
		this.numero = numero;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public Long getNumero() {
		return numero;
	}

}
