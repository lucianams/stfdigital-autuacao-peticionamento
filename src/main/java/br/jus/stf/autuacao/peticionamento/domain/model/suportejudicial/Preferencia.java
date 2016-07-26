package br.jus.stf.autuacao.peticionamento.domain.model.suportejudicial;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.preferencia.PreferenciaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 06.04.2016
 */
@Entity
@Table(name = "PREFERENCIA", schema = "PETICIONAMENTO")
public class Preferencia extends EntitySupport<Preferencia, PreferenciaId> {
	
	public static final PreferenciaId PREFERENCIA_CRIMINAL = new PreferenciaId(2L);
	
	public static final PreferenciaId PREFERENCIA_ELEITORAL = new PreferenciaId(3L);
	
	@EmbeddedId
	private PreferenciaId id;
	
	@Column(name = "NOM_PREFERENCIA")
	private String nome;
	
	public Preferencia() {
		// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	/**
	 * @param id
	 * @param nome
	 */
	public Preferencia(PreferenciaId id, String nome) {
		Validate.notNull(id, "Id preferencia.");
		Validate.notBlank(nome, "Nome requerido.");
		
		this.id = id;
		this.nome = nome;
	}
	
	/**
	 * @return
	 */
	public String nome() {
		return nome;
	}
	
	public boolean isCriminalEleitoral() {
		// TODO: Verificar uma forma melhor de implementar essa verificação
		return PREFERENCIA_CRIMINAL.sameValueAs(id) || PREFERENCIA_ELEITORAL.sameValueAs(id);
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", id.toString(), nome);
	}
	
	@Override
	public PreferenciaId identity() {
		return id;
	}

}
