package br.jus.stf.autuacao.peticionamento.domain.model.documento;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.documento.TipoDocumentoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 07.04.2016
 */
@Entity
@Table(name = "TIPO_ANEXO", schema = "PETICIONAMENTO")
public class TipoAnexo extends EntitySupport<TipoAnexo, TipoDocumentoId> {
	
	@EmbeddedId
	private TipoDocumentoId id;
	
	@Column(name = "NOM_TIPO_DOCUMENTO", nullable = false)
	private String nome;
	
	public TipoAnexo() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	public TipoAnexo(TipoDocumentoId id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public String nome() {
		return nome;
	}
	
	@Override
	public String toString() {
		return String.format("%d - %s", id.toLong(), nome);
	}
	
	@Override
	public TipoDocumentoId identity() {
		return id;
	}

}
