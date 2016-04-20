package br.jus.stf.autuacao.peticionamento.domain;

import static java.util.Comparator.comparing;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.peticionamento.domain.model.Anexo;
import br.jus.stf.autuacao.peticionamento.domain.model.Envolvido;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticao;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.classe.ClassePeticionavel;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionador;
import br.jus.stf.core.shared.processo.Polo;
import br.jus.stf.core.shared.protocolo.Protocolo;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.01.2016
 */
@Component
public class PeticaoFactory {

	public Peticao novaPeticao(Protocolo protocolo, ClassePeticionavel classe, OrgaoPeticionador orgao,
			List<String> poloAtivo, List<String> poloPassivo, Set<Anexo> anexos, Peticionador peticionador) {
    	Set<Envolvido> envolvidos = new TreeSet<>(comparing(Envolvido::apresentacao));
    	
    	poloAtivo.forEach(apresentacao -> envolvidos.add(new Envolvido(apresentacao, Polo.ATIVO, null)));
    	poloPassivo.forEach(apresentacao -> envolvidos.add(new Envolvido(apresentacao, Polo.PASSIVO, null)));
    	return new Peticao(protocolo, classe, orgao, envolvidos, anexos, peticionador);
    }

}
