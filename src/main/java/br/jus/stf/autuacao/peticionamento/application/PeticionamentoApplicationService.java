package br.jus.stf.autuacao.peticionamento.application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.jus.stf.autuacao.peticionamento.application.commands.CadastrarAnexoCommand;
import br.jus.stf.autuacao.peticionamento.application.commands.PeticionarCommand;
import br.jus.stf.autuacao.peticionamento.application.commands.PeticionarOrgaoCommand;
import br.jus.stf.autuacao.peticionamento.domain.AnexoAdapter;
import br.jus.stf.autuacao.peticionamento.domain.EnvolvidoAdapter;
import br.jus.stf.autuacao.peticionamento.domain.PeticaoFactory;
import br.jus.stf.autuacao.peticionamento.domain.ProtocoloAdapter;
import br.jus.stf.autuacao.peticionamento.domain.model.Anexo;
import br.jus.stf.autuacao.peticionamento.domain.model.Envolvido;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticao;
import br.jus.stf.autuacao.peticionamento.domain.model.PeticaoRepository;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.classe.ClassePeticionavel;
import br.jus.stf.autuacao.peticionamento.domain.model.classe.ClassePeticionavelRepository;
import br.jus.stf.autuacao.peticionamento.domain.model.documento.TipoAnexo;
import br.jus.stf.autuacao.peticionamento.domain.model.documento.TipoAnexoRepository;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionadorRepository;
import br.jus.stf.autuacao.peticionamento.domain.model.preferencia.Preferencia;
import br.jus.stf.autuacao.peticionamento.domain.model.preferencia.PreferenciaRepository;
import br.jus.stf.core.framework.component.command.Command;
import br.jus.stf.core.framework.domaindrivendesign.ApplicationService;
import br.jus.stf.core.shared.classe.ClasseId;
import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.DocumentoTemporarioId;
import br.jus.stf.core.shared.documento.TipoDocumentoId;
import br.jus.stf.core.shared.identidade.PessoaId;
import br.jus.stf.core.shared.preferencia.PreferenciaId;
import br.jus.stf.core.shared.processo.Polo;
import br.jus.stf.core.shared.processo.Sigilo;
import br.jus.stf.core.shared.processo.TipoProcesso;
import br.jus.stf.core.shared.protocolo.Protocolo;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.01.2016
 */
@ApplicationService
public class PeticionamentoApplicationService {

    @Autowired
    private PeticaoRepository peticaoRepository;
    
    @Autowired
    private ClassePeticionavelRepository classeRepository;
    
    @Autowired
    private TipoAnexoRepository tipoAnexoRepository;
    
    @Autowired
    private OrgaoPeticionadorRepository orgaoRepository;
    
    @Autowired
    private PreferenciaRepository preferenciaRepository;
    
    @Autowired
    private ProtocoloAdapter protocoloAdapter; 
    
    @Autowired
    private PeticaoFactory peticaoFactory;
    
    @Autowired
    private AnexoAdapter anexoAdapter;
    
    @Autowired
    private EnvolvidoAdapter envolvidoAdapter;

    @Transactional
    @Command(description = "Nova Petição", startProcess = true, listable = false)
    public void handle(PeticionarCommand command) {
        Protocolo protocolo = protocoloAdapter.novoProtocolo();
        ClassePeticionavel classe = classeRepository.findOne(new ClasseId(command.getClasseId()));
		List<DocumentoTemporarioId> documentosTemporarios = new ArrayList<>(0);
		
		command.getAnexos().forEach(anexo -> documentosTemporarios.add(new DocumentoTemporarioId(anexo.getDocumentoId())));
        
		Set<Anexo> anexos = command.getAnexos().stream().map(anexoDto -> criarAnexo(anexoDto)).collect(Collectors.toSet());
		Sigilo sigilo = Sigilo.valueOf(command.getSigilo());
		//TODO: Alterar para pegar dados do peticionador pelo usuário da sessão.
		Peticionador peticionador = new Peticionador("USUARIO_FALSO", new PessoaId(1L));
		Set<Envolvido> envolvidos = new HashSet<>(0);
        
		envolvidos.addAll(criarEnvolvidos(command.getPoloAtivo(), Polo.ATIVO));
        envolvidos.addAll(criarEnvolvidos(command.getPoloPassivo(), Polo.PASSIVO));
        
		Set<Preferencia> preferencias = Optional.ofNullable(command.getPreferencias()).isPresent()
				? command.getPreferencias().stream().map(pref -> preferenciaRepository.findOne(new PreferenciaId(pref)))
						.collect(Collectors.toSet()) : null;
		TipoProcesso tipoProcesso = TipoProcesso.valueOf(command.getTipoProcesso());
		Peticao peticao = peticaoFactory.novaPeticao(protocolo, classe, preferencias, null, envolvidos, anexos, sigilo, tipoProcesso, peticionador);
        
        peticaoRepository.save(peticao);
    }
    
    @Transactional
    @Command(description = "Nova Petição com Representação de Órgão", startProcess = true, listable = false)
    public void handle(PeticionarOrgaoCommand command) {
        Protocolo protocolo = protocoloAdapter.novoProtocolo();
        ClassePeticionavel classe = classeRepository.findOne(new ClasseId(command.getClasseId()));
		List<DocumentoTemporarioId> documentosTemporarios = new ArrayList<>(0);
		
		command.getAnexos().forEach(anexo -> documentosTemporarios.add(new DocumentoTemporarioId(anexo.getDocumentoId())));
        
		OrgaoPeticionador orgao = orgaoRepository.findOne(new PessoaId(command.getOrgaoId()));
		Set<Anexo> anexos = command.getAnexos().stream().map(anexoDto -> criarAnexo(anexoDto)).collect(Collectors.toSet());
		Sigilo sigilo = Sigilo.valueOf(command.getSigilo());
		//TODO: Alterar para pegar dados do peticionador pelo usuário/representante da sessão.
		Peticionador peticionador = new Peticionador("USUARIO_FALSO", orgao.associados().iterator().next().pessoa());
		Set<Envolvido> envolvidos = new HashSet<>(0);
        
		envolvidos.addAll(criarEnvolvidos(command.getPoloAtivo(), Polo.ATIVO));
        envolvidos.addAll(criarEnvolvidos(command.getPoloPassivo(), Polo.PASSIVO));
        
        Set<Preferencia> preferencias = Optional.ofNullable(command.getPreferencias()).isPresent()
				? command.getPreferencias().stream().map(pref -> preferenciaRepository.findOne(new PreferenciaId(pref)))
						.collect(Collectors.toSet()) : null;
		TipoProcesso tipoProcesso = TipoProcesso.valueOf(command.getTipoProcesso());
		Peticao peticao = peticaoFactory.novaPeticao(protocolo, classe, preferencias, orgao, envolvidos, anexos, sigilo, tipoProcesso, peticionador);
        
        peticaoRepository.save(peticao);
    }
    
    /**
     * Cria um objeto anexo.
     * @param anexoCommand Dados do anexo oriundos do front-end.
     * @return Objeto anexo.
     */
    private Anexo criarAnexo(CadastrarAnexoCommand anexoCommand) {
	    List<DocumentoTemporarioId> documentosTemporarios = new ArrayList<DocumentoTemporarioId>();
	    
	    documentosTemporarios.add(new DocumentoTemporarioId(anexoCommand.getDocumentoId()));
	    
	    TipoAnexo tipo = tipoAnexoRepository.findOne(new TipoDocumentoId(anexoCommand.getTipoDocumentoId()));
	    DocumentoId documentoId = anexoAdapter.salvar(documentosTemporarios).get(0);
	    
	    return new Anexo(tipo, documentoId);
    }
    
	/**
	 * Aloca os IDs das pessoas no devido contexto e retorna
	 * uma lista de envolvidos para associação com a Petição.
	 * 
	 * @param nomes
	 * @param polo
	 * @return lista de envolvidos
	 */
	private Set<Envolvido> criarEnvolvidos(List<String> nomes, Polo polo) {
		Set<PessoaId> pessoas = envolvidoAdapter.pessoasEnvolvidas(nomes);
		Set<Envolvido> envolvidos = new HashSet<Envolvido>(0);
        int index = 0;
        
		for(PessoaId pessoa : pessoas) {
        	envolvidos.add(new Envolvido(nomes.get(index++), polo, pessoa));
		}
		return envolvidos;
	}
}
