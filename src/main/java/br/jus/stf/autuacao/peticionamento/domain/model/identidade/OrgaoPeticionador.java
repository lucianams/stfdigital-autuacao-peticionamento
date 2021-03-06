package br.jus.stf.autuacao.peticionamento.domain.model.identidade;

import static javax.persistence.CascadeType.ALL;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.identidade.PessoaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 08.04.2016
 */
@Entity
@Table(name = "ORGAO_PETICIONADOR", schema = "PETICIONAMENTO")
public class OrgaoPeticionador extends EntitySupport<OrgaoPeticionador, PessoaId> {
	
	@EmbeddedId
	private PessoaId id;
	
	@Column(name = "NOM_PESSOA", nullable = false)
	private String nome;
	
	@OneToMany(cascade = ALL)
    @JoinColumn(name = "SEQ_PESSOA_ORGAO", referencedColumnName = "SEQ_PESSOA", nullable = false)
	private Set<Associado> associados = new HashSet<>();
	
	public OrgaoPeticionador() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	/**
	 * @param id
	 * @param nome
	 * @param associados
	 */
	public OrgaoPeticionador(PessoaId id, String nome, Set<Associado> associados) {
		Validate.notNull(id, "Identificador requerido.");
		Validate.notBlank(nome, "Nome requerido.");
		
		this.id = id;
		this.nome = nome;
		this.associados = Optional.ofNullable(associados).orElse(new HashSet<>(0));
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
	public Set<Associado> associados() {
		return Collections.unmodifiableSet(associados);
	}
	
	/**
	 * @param pessoa
	 * @return
	 */
	public boolean isRepresentadoPor(PessoaId pessoa) {
		return associados.stream().anyMatch(associado -> associado.isRepresentante(pessoa));
	}
	
	@Override
	public String toString() {
		return String.format("%d - %s", id.toLong(), nome);
	}
	
	@Override
	public PessoaId identity() {
		return id;
	}

}
