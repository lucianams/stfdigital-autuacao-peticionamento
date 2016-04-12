package br.jus.stf.autuacao.peticionamento.domain.model;

import java.io.IOException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.03.2016
 */
@Entity
@Table(name = "EVENTO", schema = "PETICIONAMENTO")
public class Evento extends EntitySupport<Evento, Long> {

	@Id
	@Column(name = "SEQ_EVENTO")
	@SequenceGenerator(name = "EVENTO_ID", sequenceName = "PETICIONAMENTO.SEQ_EVENTO", allocationSize = 1)
	@GeneratedValue(generator = "EVENTO_ID", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "NOM_EVENTO")
	private String tipo;
	
	@Column(name = "DAT_CRIACAO")
	private Date criacao;
	
	@Column(name = "BIN_DETALHE")
	@Lob
	private String detalhes;
	
	@Column(name = "TIP_STATUS")
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
