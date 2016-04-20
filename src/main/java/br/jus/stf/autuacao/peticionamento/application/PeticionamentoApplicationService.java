package br.jus.stf.autuacao.peticionamento.application;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.jus.stf.autuacao.peticionamento.application.commands.PeticionarCommand;
import br.jus.stf.autuacao.peticionamento.domain.PeticaoFactory;
import br.jus.stf.autuacao.peticionamento.domain.ProtocoloAdapter;
import br.jus.stf.autuacao.peticionamento.domain.model.Anexo;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticao;
import br.jus.stf.autuacao.peticionamento.domain.model.PeticaoRepository;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.classe.ClassePeticionavel;
import br.jus.stf.autuacao.peticionamento.domain.model.classe.ClassePeticionavelRepository;
import br.jus.stf.autuacao.peticionamento.domain.model.documento.TipoAnexo;
import br.jus.stf.autuacao.peticionamento.domain.model.documento.TipoAnexoRepository;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionadorRepository;
import br.jus.stf.core.shared.classe.ClasseId;
import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.TipoDocumentoId;
import br.jus.stf.core.shared.identidade.PessoaId;
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
    
    @Autowired
    private TipoAnexoRepository tipoAnexoRepository;
    
    @Autowired
    private OrgaoPeticionadorRepository orgaoRepository;
    
    @Autowired
    private ProtocoloAdapter protocoloAdapter; 
    
    @Autowired
    private PeticaoFactory peticaoFactory;

    @Transactional
    public void handle(PeticionarCommand command) {
        Protocolo protocolo = protocoloAdapter.novoProtocolo();
        ClassePeticionavel classe = classeRepository.findOne(new ClasseId(command.getClasseId()));
		OrgaoPeticionador orgao = Optional.ofNullable(command.getOrgaoId()).isPresent()
				? orgaoRepository.findOne(new PessoaId(command.getOrgaoId())) : null;
        Set<Anexo> anexos = command.getAnexos().stream().map(anexoDto -> {
        	TipoAnexo tipo = tipoAnexoRepository.findOne(new TipoDocumentoId(anexoDto.getTipo()));
        	
        	return new Anexo(tipo, new DocumentoId(anexoDto.getDocumento()));
        }).collect(Collectors.toSet());
        //TODO: Alterar para pegar dados do peticionador pelo usuário da sessão.
        Peticionador peticionador = new Peticionador("USUARIO_FALSO", new PessoaId(1L));
        
        Peticao peticao = peticaoFactory.novaPeticao(protocolo, classe, orgao, command.getPoloAtivo(), command.getPoloPassivo(), anexos, peticionador);
        
        peticaoRepository.save(peticao);
    }

}
