package br.jus.stf.autuacao.peticionamento.domain.model;

import static java.util.Comparator.comparing;
import static javax.persistence.CascadeType.ALL;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.peticionamento.domain.model.classe.ClassePeticionavel;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionador;
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
@Table(name = "PETICAO", schema = "PETICIONAMENTO")
public class Peticao extends EntitySupport<Peticao, ProtocoloId> implements AggregateRoot<Peticao, ProtocoloId> {
	
    @EmbeddedId
    private ProtocoloId protocoloId;
	
    @ManyToOne
    @JoinColumn(name = "SIG_CLASSE", nullable = false)
	private ClassePeticionavel classe;

    @ManyToOne
    @JoinColumn(name = "SEQ_ORGAO", referencedColumnName = "SEQ_PESSOA")
	private OrgaoPeticionador orgao;
    
    @OneToMany(cascade = ALL)
    @JoinColumn(name = "SEQ_PROTOCOLO", referencedColumnName = "SEQ_PROTOCOLO", nullable = false)
    private Set<Envolvido> envolvidos = new TreeSet<>(comparing(Envolvido::apresentacao));
    
    @OneToMany(cascade = ALL)
    @JoinColumn(name = "SEQ_PROTOCOLO", referencedColumnName = "SEQ_PROTOCOLO", nullable = false)
    private Set<Anexo> anexos = new HashSet<>();
    
    @OneToMany(cascade = ALL)
    @JoinTable(name = "PETICAO_EVENTO", schema = "PETICIONAMENTO", joinColumns = @JoinColumn(name = "SEQ_PROTOCOLO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_EVENTO", nullable = false))
    private Set<Evento> eventos = new TreeSet<>(comparing(Evento::criacao));
    
    @Embedded
    @Column(nullable = false)
    private Peticionador peticionador;
    
    @Column(name = "DAT_PETICIONAMENTO", nullable = false)
    private Date dataPeticionamento = new Date();
    
    public Peticao() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }

	public Peticao(Protocolo protocolo, ClassePeticionavel classe, OrgaoPeticionador orgao, Set<Envolvido> envolvidos, Set<Anexo> anexos, Peticionador peticionador) {
    	Validate.notNull(protocolo, "Protocolo requerido.");
    	Validate.notNull(classe, "Classe requerida.");
    	Validate.notEmpty(envolvidos, "Envolvidos requeridos.");
    	Validate.notEmpty(anexos, "Anexos requeridos.");
    	Validate.notNull(peticionador, "Peticionador requerido.");
		Validate.isTrue(!Optional.ofNullable(orgao).isPresent() || orgao.isRepresentadoPor(peticionador.pessoa()),
				"Peticionador não é representante do Órgão.");
		
    	this.protocoloId = protocolo.identity();
    	this.classe = classe;
        this.orgao = orgao;
        this.envolvidos = envolvidos;
        this.anexos = anexos;
        this.peticionador = peticionador;
        
        registrarEvento(new PeticaoRegistrada(protocoloId.toLong(), protocolo.toString()));
        
        envolvidos.forEach(envolvido -> registrarEvento(new EnvolvidoRegistrado(protocoloId.toLong(), protocolo.toString(), envolvido.apresentacao())));
    }

	private void registrarEvento(DomainEvent<?> evento) {
		eventos.add(new Evento(evento));
	}
    
	@Override
	public ProtocoloId identity() {
		return protocoloId;
	}
	
}
