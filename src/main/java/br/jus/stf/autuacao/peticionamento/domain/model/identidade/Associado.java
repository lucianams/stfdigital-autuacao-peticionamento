package br.jus.stf.autuacao.peticionamento.domain.model.identidade;

import java.util.Optional;

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

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;
import br.jus.stf.core.shared.identidade.PessoaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 08.04.2016
 */
@Entity
@Table(name = "ASSOCIADO", schema = "PETICIONAMENTO")
public class Associado extends ValueObjectSupport<Associado> {
	
	@Id
	@Column(name = "SEQ_ASSOCIADO")
	@SequenceGenerator(name = "ASSOCIADO_ID", sequenceName = "PETICIONAMENTO.SEQ_ASSOCIADO", allocationSize = 1)
	@GeneratedValue(generator = "ASSOCIADO_ID", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Embedded
	@Column(nullable = false)
	private PessoaId pessoa;
	
	@Column(name = "NOM_PESSOA", nullable = false)
	private String nome;
	
	@Column(name = "TIP_ASSOCIADO", nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoAssociado tipo;
	
	@Column(name = "DSC_CARGO_FUNCAO")
	private String cargoFuncao;
	
	public Associado() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	public Associado(PessoaId pessoa, String nome, TipoAssociado tipo, String cargoFuncao) {
		this.pessoa = pessoa;
		this.nome = nome;
		this.tipo = tipo;
		this.cargoFuncao = cargoFuncao;
	}
	
	public Long toLong() {
		return id;
	}
	
	public PessoaId pessoa() {
		return pessoa;
	}
	
	public String nome() {
		return nome;
	}
	
	public TipoAssociado tipo() {
		return tipo;
	}
	
	public String cargoFuncao() {
		return cargoFuncao;
	}
	
	public boolean isRepresentante(PessoaId pessoa) {
		return Optional.ofNullable(pessoa).isPresent() && pessoa.equals(this.pessoa)
				&& TipoAssociado.REPRESENTANTE.equals(this.tipo);
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", nome, tipo.descricao());
	}

}
