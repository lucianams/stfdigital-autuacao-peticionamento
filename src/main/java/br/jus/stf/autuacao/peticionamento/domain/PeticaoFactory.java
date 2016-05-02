package br.jus.stf.autuacao.peticionamento.domain;

import java.util.Set;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.peticionamento.domain.model.Anexo;
import br.jus.stf.autuacao.peticionamento.domain.model.Envolvido;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticao;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.classe.ClassePeticionavel;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.preferencia.Preferencia;
import br.jus.stf.core.shared.protocolo.Protocolo;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.01.2016
 */
@Component
public class PeticaoFactory {

	public Peticao novaPeticao(Protocolo protocolo, ClassePeticionavel classe, Set<Preferencia> preferencias,
			OrgaoPeticionador orgao, Set<Envolvido> envolvidos, Set<Anexo> anexos, Peticionador peticionador) {
    	
    	return new Peticao(protocolo, classe, preferencias, orgao, envolvidos, anexos, peticionador);
    }

}
