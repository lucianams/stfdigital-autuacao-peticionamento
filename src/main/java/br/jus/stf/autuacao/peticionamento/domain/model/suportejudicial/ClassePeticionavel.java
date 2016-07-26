package br.jus.stf.autuacao.peticionamento.domain.model.suportejudicial;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.classe.ClasseId;
import br.jus.stf.core.shared.processo.TipoProcesso;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 06.04.2016
 */
@Entity
@Table(name = "CLASSE_PETICIONAVEL", schema = "PETICIONAMENTO")
public class ClassePeticionavel extends EntitySupport<ClassePeticionavel, ClasseId> {
	
	@EmbeddedId
	private ClasseId sigla;
	
	@Column(name = "NOM_CLASSE")
	private String nome;
	
	@OneToMany(cascade = ALL, fetch = EAGER)
    @JoinTable(name = "CLASSE_PREFERENCIA", schema = "PETICIONAMENTO", joinColumns = @JoinColumn(name = "SIG_CLASSE", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_PREFERENCIA", nullable = false))
	private Set<Preferencia> preferencias = new HashSet<>(0);
	
	@Column(name = "TIP_PROCESSO", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoProcesso tipo;
	
	public ClassePeticionavel() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	/**
	 * @param sigla
	 * @param nome
	 * @param tipo
	 * @param preferencias
	 */
	public ClassePeticionavel(ClasseId sigla, String nome, TipoProcesso tipo, Set<Preferencia> preferencias) {
		Validate.notNull(sigla, "Sigla requerida.");
		Validate.notBlank(nome, "Nome requerido.");
		Validate.notNull(tipo, "Tipo requerido.");
		
		this.sigla = sigla;
		this.nome = nome;
		this.tipo = tipo;
		this.preferencias = Optional.ofNullable(preferencias).orElse(new HashSet<>(0));
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
	public TipoProcesso tipo() {
		return tipo;
	}
	
	/**
	 * @return
	 */
	public Set<Preferencia> preferencias() {
		return Collections.unmodifiableSet(preferencias);
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", sigla.toString(), nome);
	}
	
	@Override
	public ClasseId identity() {
		return sigla;
	}

}
