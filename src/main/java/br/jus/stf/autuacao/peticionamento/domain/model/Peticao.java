package br.jus.stf.autuacao.peticionamento.domain.model;

import static java.util.Comparator.comparing;
import static javax.persistence.CascadeType.ALL;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.suportejudicial.ClassePeticionavel;
import br.jus.stf.autuacao.peticionamento.domain.model.suportejudicial.Preferencia;
import br.jus.stf.core.framework.domaindrivendesign.AggregateRoot;
import br.jus.stf.core.framework.domaindrivendesign.DomainEvent;
import br.jus.stf.core.framework.domaindrivendesign.EntitySupport;
import br.jus.stf.core.shared.eventos.EnvolvidoRegistrado;
import br.jus.stf.core.shared.eventos.PeticaoRegistrada;
import br.jus.stf.core.shared.processo.Polo;
import br.jus.stf.core.shared.processo.Sigilo;
import br.jus.stf.core.shared.processo.TipoProcesso;
import br.jus.stf.core.shared.protocolo.Numero;
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
    
    @Embedded
    @AttributeOverrides( {
        @AttributeOverride(name="numero", column = @Column(name="NUM_PETICAO", nullable = false)),
        @AttributeOverride(name="ano", column = @Column(name="NUM_ANO", nullable = false))
    })
    private Numero numero;
    
    @OneToMany(cascade = ALL)
    @JoinTable(name = "PETICAO_PREFERENCIA", schema = "PETICIONAMENTO", joinColumns = @JoinColumn(name = "SEQ_PROTOCOLO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_PREFERENCIA", nullable = false))
    private Set<Preferencia> preferencias = new HashSet<>(0);

    @ManyToOne
    @JoinColumn(name = "SEQ_ORGAO", referencedColumnName = "SEQ_PESSOA")
	private OrgaoPeticionador orgao;
    
    @OneToMany(cascade = ALL)
    @JoinColumn(name = "SEQ_PROTOCOLO", referencedColumnName = "SEQ_PROTOCOLO", nullable = false)
    private Set<Envolvido> envolvidos = new TreeSet<>(comparing(Envolvido::apresentacao));
    
    @OneToMany(cascade = ALL)
    @JoinColumn(name = "SEQ_PROTOCOLO", referencedColumnName = "SEQ_PROTOCOLO", nullable = false)
    private Set<Anexo> anexos = new HashSet<>(0);
    
    @OneToMany(cascade = ALL)
    @JoinTable(name = "PETICAO_EVENTO", schema = "PETICIONAMENTO", joinColumns = @JoinColumn(name = "SEQ_PROTOCOLO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_EVENTO", nullable = false))
    private Set<Evento> eventos = new TreeSet<>(comparing(Evento::criacao));
    
    @Embedded
    @Column(nullable = false)
    private Peticionador peticionador;
    
    @Column(name = "DAT_PETICIONAMENTO", nullable = false)
    private Date dataPeticionamento = new Date();
    
    @Column(name = "TIP_SIGILO", nullable = false)
    @Enumerated(EnumType.STRING)
    private Sigilo sigilo;
    
    @Column(name = "TIP_PROCESSO", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoProcesso tipoProcesso;
    
    public Peticao() {
    	// Deve ser usado apenas pelo Hibernate, que sempre usa o construtor default antes de popular uma nova instância.
    }

	/**
	 * @param protocolo
	 * @param classe
	 * @param preferencias
	 * @param orgao
	 * @param envolvidos
	 * @param anexos
	 * @param sigilo
	 * @param tipoProcesso
	 * @param peticionador
	 */
	public Peticao(Protocolo protocolo, ClassePeticionavel classe, Set<Preferencia> preferencias,
			OrgaoPeticionador orgao, Set<Envolvido> envolvidos, Set<Anexo> anexos, Sigilo sigilo,
			TipoProcesso tipoProcesso, Peticionador peticionador) {
    	Validate.notNull(protocolo, "Protocolo requerido.");
    	Validate.notNull(classe, "Classe requerida.");
    	Validate.notEmpty(envolvidos, "Envolvidos requeridos.");
    	Validate.isTrue(isEnvolvidosValidos(envolvidos), "Petição deve ter ao menos um envolvido para o polo ativo.");
    	Validate.notEmpty(anexos, "Anexos requeridos.");
    	Validate.notNull(sigilo, "Sigilo requerido.");
    	Validate.notNull(tipoProcesso, "Tipo do processo requerido.");
    	Validate.notNull(peticionador, "Peticionador requerido.");
		Validate.isTrue(!Optional.ofNullable(orgao).isPresent() || orgao.isRepresentadoPor(peticionador.pessoa()),
				"Peticionador não é representante do Órgão.");
		Validate.isTrue(tipoProcesso.equals(classe.tipo()), "O tipo da petição e da classe são incompatíveis.");
		Validate.isTrue(!Optional.ofNullable(preferencias).isPresent() || classe.preferencias().containsAll(preferencias),
				"Alguma(s) preferência(s) não pertence(m) à classe selecionada.");
		
    	this.protocoloId = protocolo.identity();
    	this.numero = protocolo.numero();
    	this.classe = classe;
    	this.preferencias = Optional.ofNullable(preferencias).orElse(new HashSet<>(0));
        this.orgao = orgao;
        this.envolvidos = envolvidos;
        this.anexos = anexos;
        this.sigilo = sigilo;
        this.tipoProcesso = tipoProcesso;
        this.peticionador = peticionador;
        
		registrarEvento(new PeticaoRegistrada(protocoloId.toLong(), protocolo.toString(), classe.identity().toString(),
				tipoProcesso.toString(), sigilo.toString(), isCriminalEleitoral()));
        
		envolvidos.forEach(envolvido -> registrarEvento(new EnvolvidoRegistrado(protocoloId.toLong(),
				protocolo.toString(), envolvido.apresentacao(), envolvido.pessoa().toLong())));
    }

	private void registrarEvento(DomainEvent<?> evento) {
		eventos.add(new Evento(evento));
	}
	
	/**
	 * @return
	 */
	public Numero numero() {
    	return numero;
    }
	
	/**
	 * @return
	 */
	public Set<Envolvido> envolvidos(){
		return Collections.unmodifiableSet(envolvidos);
	}
	
	public Boolean isCriminalEleitoral() {
		return preferencias.stream().anyMatch(preferencia -> preferencia.isCriminalEleitoral());
	}
	
	/**
	 * @return
	 */
	public TipoProcesso tipoProcesso() {
		return tipoProcesso;
	}
    
	@Override
	public ProtocoloId identity() {
		return protocoloId;
	}
	
	private Boolean isEnvolvidosValidos(Set<Envolvido> envolvidos) {
		if (envolvidos.isEmpty()) {
			return false;
		}
		
		return envolvidos.stream().anyMatch(envolvido -> Polo.ATIVO.equals(envolvido.polo()));
	}
	
}
