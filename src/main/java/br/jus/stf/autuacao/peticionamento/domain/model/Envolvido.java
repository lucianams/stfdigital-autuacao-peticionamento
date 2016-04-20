package br.jus.stf.autuacao.peticionamento.domain.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;
import br.jus.stf.core.shared.identidade.PessoaId;
import br.jus.stf.core.shared.processo.Polo;

/**
 * @author Rodrigo Barreiros
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 29.02.2016
 */
@Entity
@Table(name = "ENVOLVIDO", schema = "PETICIONAMENTO")
public class Envolvido extends ValueObjectSupport<Envolvido> {
	
	@Id
	@Column(name = "SEQ_ENVOLVIDO")
	@SequenceGenerator(name = "ENVOLVIDO_ID", sequenceName = "PETICIONAMENTO.SEQ_ENVOLVIDO", allocationSize = 1)
	@GeneratedValue(generator = "ENVOLVIDO_ID", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "NOM_APRESENTACAO", nullable = false)
	private String apresentacao;
	
	@Column(name = "TIP_POLO", nullable = false)
	@Enumerated(EnumType.STRING)
	private Polo polo;
	
	@Embedded
	private PessoaId pessoa;
	
	public Envolvido() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	public Envolvido(String apresentacao, Polo polo, PessoaId pessoa) {
		Validate.notBlank(apresentacao, "Apresentação requerida.");
		Validate.notNull(polo, "Polo requerido.");
		
		this.apresentacao = apresentacao;
		this.polo = polo;
		this.pessoa = pessoa;
	}
	
	public Long toLong() {
		return id;
	}
	
	public String apresentacao() {
		return apresentacao;
	}
	
	public Polo polo() {
		return polo;
	}
	
	public PessoaId pessoa() {
		return pessoa;
	}
	
	@Override
	public String toString() {
		return String.format("%s [%s]", apresentacao, polo.descricao());
	}

}
