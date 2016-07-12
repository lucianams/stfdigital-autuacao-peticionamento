package br.jus.stf.autuacao.peticionamento.domain.model.identidade;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

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
	
	/**
	 * @param id
	 * @param pessoa
	 * @param nome
	 * @param tipo
	 * @param cargoFuncao
	 */
	public Associado(Long id, PessoaId pessoa, String nome, TipoAssociado tipo, String cargoFuncao) {
		Validate.notNull(id, "Identificador requerido.");
		Validate.notNull(pessoa, "Pessoa requerida.");
		Validate.notBlank(nome, "Nome requerido.");
		Validate.notNull(tipo, "Tipo requerido.");
		
		this.id = id;
		this.pessoa = pessoa;
		this.nome = nome;
		this.tipo = tipo;
		this.cargoFuncao = cargoFuncao;
	}
	
	/**
	 * @return
	 */
	public Long toLong() {
		return id;
	}
	
	/**
	 * @return
	 */
	public PessoaId pessoa() {
		return pessoa;
	}
	
	/**
	 * @return
	 */
	public String nome() {
		return nome;
	}
	
	/**
	 * @return
	 */
	public TipoAssociado tipo() {
		return tipo;
	}
	
	/**
	 * @return
	 */
	public String cargoFuncao() {
		return cargoFuncao;
	}
	
	/**
	 * @param pessoa
	 * @return
	 */
	public boolean isRepresentante(PessoaId pessoa) {
		return Optional.ofNullable(pessoa).isPresent() && TipoAssociado.REPRESENTANTE.equals(tipo)
				&& pessoa.sameValueAs(this.pessoa);
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", nome, tipo.descricao());
	}

}
