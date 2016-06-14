package br.jus.stf.autuacao.peticionamento.application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.jus.stf.autuacao.peticionamento.application.commands.CadastrarAnexoCommand;
import br.jus.stf.autuacao.peticionamento.application.commands.PeticionarCommand;
import br.jus.stf.autuacao.peticionamento.domain.DocumentoAdapter;
import br.jus.stf.autuacao.peticionamento.domain.PessoaAdapter;
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
import br.jus.stf.core.framework.component.command.Command;
import br.jus.stf.core.shared.classe.ClasseId;
import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.DocumentoTemporarioId;
import br.jus.stf.core.shared.documento.TipoDocumentoId;
import br.jus.stf.core.shared.identidade.PessoaId;
import br.jus.stf.core.shared.processo.Polo;
import br.jus.stf.core.shared.processo.Sigilo;
import br.jus.stf.core.shared.protocolo.Protocolo;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.01.2016
 */
@Component
public class PeticionamentoApplicationService {

    @Autowired
    private PeticaoRepository peticaoRepository;
    
    @Autowired
    private ClassePeticionavelRepository classeRepository;
    
    //@Autowired
    //private PreferenciaRepository preferenciaRepository;
    
    @Autowired
    private TipoAnexoRepository tipoAnexoRepository;
    
    @Autowired
    private OrgaoPeticionadorRepository orgaoRepository;
    
    @Autowired
    private ProtocoloAdapter protocoloAdapter; 
    
    @Autowired
    private PeticaoFactory peticaoFactory;
    
    @Autowired
    private DocumentoAdapter documentoAdapter;
    
    @Autowired
    private PessoaAdapter pessoaAdapter;

    @Transactional
    @Command(description = "Nova petição", startProcess = true, listable = false)
    public void handle(PeticionarCommand command) {
        Protocolo protocolo = protocoloAdapter.novoProtocolo();
        ClassePeticionavel classe = classeRepository.findOne(new ClasseId(command.getClasseId()));
		List<DocumentoTemporarioId> documentosTemporarios = new ArrayList<DocumentoTemporarioId>();
		command.getAnexos().forEach(anexo -> documentosTemporarios.add(new DocumentoTemporarioId(anexo.getDocumentoId())));
        OrgaoPeticionador orgao = Optional.ofNullable(command.getOrgaoId()).isPresent()	? orgaoRepository.findOne(new PessoaId(command.getOrgaoId())) : null;
		Set<Anexo> anexos = command.getAnexos().stream().map(anexoDto -> criarAnexo(anexoDto)).collect(Collectors.toSet());
		Sigilo sigilo = Sigilo.valueOf(command.getSigilo());
		//TODO: Alterar para pegar dados do peticionador pelo usuário da sessão.
		Peticionador peticionador = new Peticionador("USUARIO_FALSO", Optional.ofNullable(orgao).isPresent()
				? orgao.associados().iterator().next().pessoa() : new PessoaId(1L));
		
		//TODO: Verificar como reutilizar pessoas.
		Set<Envolvido> envolvidos = new HashSet<Envolvido>();
        envolvidos.addAll(criarEnvolvidos(command.getPoloAtivo(), Polo.ATIVO));
        envolvidos.addAll(criarEnvolvidos(command.getPoloPassivo(), Polo.PASSIVO));
        
		Peticao peticao = peticaoFactory.novaPeticao(protocolo, classe, null, orgao, envolvidos, anexos, sigilo, peticionador);
        
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
	    DocumentoId documentoId = documentoAdapter.salvar(documentosTemporarios).get(0);
	    
	    return new Anexo(tipo, documentoId);
    }
    
    /**
	 * Salva as pessoas, recupera os IDs e retorna uma lista de envolvidos.
	 * @param partes
	 * @param polo
	 * @param tipo
	 */
	private Set<Envolvido> criarEnvolvidos(List<String> nomes, Polo polo) {
		Set<PessoaId> pessoas = pessoaAdapter.cadastrarPessoas(nomes);
		Set<Envolvido> envolvidos = new HashSet<Envolvido>();
        int index = 0;
        
		for(PessoaId pessoa : pessoas) {
			envolvidos.add(new Envolvido(nomes.get(index++), polo, pessoa));
		}
		
		return envolvidos;
	}
}
