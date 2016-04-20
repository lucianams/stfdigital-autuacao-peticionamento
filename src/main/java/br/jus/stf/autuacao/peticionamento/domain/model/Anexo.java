package br.jus.stf.autuacao.peticionamento.domain.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.peticionamento.domain.model.documento.TipoAnexo;
import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;
import br.jus.stf.core.shared.documento.DocumentoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 07.04.2016
 */
@Entity
@Table(name = "ANEXO", schema = "PETICIONAMENTO")
public class Anexo extends ValueObjectSupport<Anexo> {
	
	@Id
	@Column(name = "SEQ_ANEXO")
	@SequenceGenerator(name = "ANEXO_ID", sequenceName = "PETICIONAMENTO.SEQ_ANEXO", allocationSize = 1)
	@GeneratedValue(generator = "ANEXO_ID", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "DSC_ANEXO", nullable = false)
	private String descricao;
	
	@ManyToOne
    @JoinColumn(name = "SEQ_TIPO_ANEXO", nullable = false)
	private TipoAnexo tipo;
	
	@Embedded
	@Column(nullable = false)
	private DocumentoId documento;
	
	public Anexo() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	public Anexo(TipoAnexo tipo, DocumentoId documento) {
		Validate.notNull(tipo, "Tipo requerido.");
		Validate.notNull(documento, "Documento requerido.");
		
		this.tipo = tipo;
		this.documento = documento;
		this.descricao = tipo.nome();
	}
	
	public Long toLong() {
		return id;
	}
	
	public String descricao() {
		return descricao;
	}
	
	public TipoAnexo tipo() {
		return tipo;
	}
	
	public DocumentoId documento() {
		return documento;
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", tipo.nome(), descricao);
	}

}
