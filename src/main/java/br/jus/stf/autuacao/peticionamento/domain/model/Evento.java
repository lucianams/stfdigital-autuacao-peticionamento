package br.jus.stf.autuacao.peticionamento.domain.model;

import java.io.IOException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.03.2016
 */
@Entity
public class Evento extends EntitySupport<Evento, Long> {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String tipo;
	
	@Column
	private Date criacao;
	
	@Column
	private String detalhes;
	
	@Column
	private Integer status = 1;
	
	public Evento() {
    	// Usado apenas pelo Jackson durante a conversação de Json para uma nova instância.
	}
	
	public Evento(Object detalhes) {
		try {
			this.detalhes = new ObjectMapper().writeValueAsString(detalhes);
		}
		catch (IOException e) {
			throw new IllegalArgumentException(String.format("Não foi possível converter o objeto: %s", detalhes), e);
		}
		this.tipo = detalhes.getClass().getSimpleName();
		criacao = new Date();
	}

	@Override
	public Long identity() {
		return id;
	}
	
	public Date criacao() {
		return criacao;
	}
	
}
