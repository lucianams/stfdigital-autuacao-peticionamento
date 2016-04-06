package br.jus.stf.autuacao.peticionamento.domain;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.peticionamento.domain.model.Envolvido;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticao;
import br.jus.stf.core.shared.classe.ClasseId;
import br.jus.stf.core.shared.protocolo.Protocolo;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.01.2016
 */
@Component
public class PeticaoFactory {

    public Peticao novaPeticao(Protocolo protocolo, ClasseId classeId, Long orgaoId, List<String> poloAtivo, List<String> poloPassivo) {
    	Set<Envolvido> envolvidos = new TreeSet<>(Comparator.comparing(Envolvido::nome));
    	poloAtivo.forEach(nome -> envolvidos.add(new Envolvido(nome)));
    	poloPassivo.forEach(nome -> envolvidos.add(new Envolvido(nome)));
    	
    	return new Peticao(protocolo, classeId, orgaoId, envolvidos);
    }

}
