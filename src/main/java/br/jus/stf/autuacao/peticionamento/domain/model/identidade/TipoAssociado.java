package br.jus.stf.autuacao.peticionamento.domain.model.identidade;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 08.04.2016
 */
public enum TipoAssociado {
	
	ASSOCIADO("Associado"),
	GESTOR("Gestor"),
	REPRESENTANTE("Representante");
	
	private String descricao;
	
	private TipoAssociado(String descricao) {
		this.descricao = descricao;
	}
	
	public String descricao() {
		return descricao;
	}

}
