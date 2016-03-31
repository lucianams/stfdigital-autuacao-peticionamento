package br.jus.stf.autuacao.peticionamento.domain.model;

import static java.util.Comparator.comparing;
import static javax.persistence.CascadeType.ALL;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import br.jus.stf.core.framework.domaindrivendesign.AggregateRoot;
import br.jus.stf.core.framework.domaindrivendesign.DomainEvent;
import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.eventos.EnvolvidoRegistrado;
import br.jus.stf.core.shared.eventos.PeticaoRegistrada;
import br.jus.stf.core.shared.protocolo.Protocolo;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.01.2016
 */
@Entity
public class Peticao extends EntitySupport<Peticao, ProtocoloId> implements AggregateRoot<Peticao, ProtocoloId> {
	
    @EmbeddedId
    private ProtocoloId protocoloId;
	
    @Column
	private String classeId;

    @Column
	private Long orgaoId;
    
    @OneToMany(cascade = ALL)
    private Set<Envolvido> envolvidos = new TreeSet<>(comparing(Envolvido::nome));
    
    @OneToMany(cascade = ALL)
    private Set<Evento> eventos = new TreeSet<>(comparing(Evento::criacao));
    
	public Peticao() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }

    public Peticao(Protocolo protocolo, String classeId, Long orgaoId, Set<Envolvido> envolvidos) {
    	this.protocoloId = protocolo.identity();
    	this.envolvidos = envolvidos;
        this.classeId = classeId;
        this.orgaoId = orgaoId;
        
        registrarEvento(new PeticaoRegistrada(protocoloId.toLong(), protocolo.toString()));
        
        envolvidos.forEach(envolvido -> registrarEvento(new EnvolvidoRegistrado(protocoloId.toLong(), protocolo.toString(), envolvido.nome())));
    }

	private void registrarEvento(DomainEvent<?> evento) {
		eventos.add(new Evento(evento));
	}
    
	@Override
	public ProtocoloId identity() {
		return protocoloId;
	}
	
}
