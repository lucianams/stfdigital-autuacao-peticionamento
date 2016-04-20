package br.jus.stf.autuacao.peticionamento.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.apache.commons.lang3.Validate;

import br.jus.stf.core.framework.domaindrivendesign.ValueObjectSupport;
import br.jus.stf.core.shared.identidade.PessoaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 19.04.2016
 */
@Embeddable
public class Peticionador extends ValueObjectSupport<Peticionador> {
	
	@Column(name = "SIG_PETICIONADOR")
	private String login;
	
	@Transient
	private PessoaId pessoa;
	
	public Peticionador() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
	}
	
	public Peticionador(String login, PessoaId pessoa) {
		Validate.notBlank(login, "Login requerido.");
		Validate.notNull(pessoa, "Pessoa requerida.");
		
		this.login = login;
		this.pessoa = pessoa;
	}
	
	public String login() {
		return login;
	}
	
	public PessoaId pessoa() {
		return pessoa;
	}
	
	@Override
	public String toString() {
		return String.format("%s", login);
	}

}
