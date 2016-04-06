package br.jus.stf.autuacao.peticionamento.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.jus.stf.autuacao.peticionamento.application.commands.PeticionarCommand;
import br.jus.stf.autuacao.peticionamento.domain.PeticaoFactory;
import br.jus.stf.autuacao.peticionamento.domain.ProtocoloAdapter;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticao;
import br.jus.stf.autuacao.peticionamento.domain.model.PeticaoRepository;
import br.jus.stf.core.shared.classe.ClasseId;
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
    private ProtocoloAdapter protocoloAdapter; 
    
    @Autowired
    private PeticaoFactory peticaoFactory;

    @Transactional
    public void handle(PeticionarCommand command) {
        Protocolo protocolo = protocoloAdapter.novoProtocolo();
        ClasseId classeId = new ClasseId(command.getClasseId());
        
        Peticao peticao = peticaoFactory.novaPeticao(protocolo, classeId, command.getOrgaoId(), command.getPoloAtivo(), command.getPoloPassivo());
        
        peticaoRepository.save(peticao);
    }

}
