package br.jus.stf.autuacao.peticionamento.domain;

import java.util.Set;

import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.peticionamento.domain.model.Anexo;
import br.jus.stf.autuacao.peticionamento.domain.model.Envolvido;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticao;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.suportejudicial.ClassePeticionavel;
import br.jus.stf.autuacao.peticionamento.domain.model.suportejudicial.Preferencia;
import br.jus.stf.core.shared.processo.Sigilo;
import br.jus.stf.core.shared.processo.TipoProcesso;
import br.jus.stf.core.shared.protocolo.Protocolo;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 03.01.2016
 */
@Component
public class PeticaoFactory {

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
	 * @return
	 */
	public Peticao novaPeticao(Protocolo protocolo, ClassePeticionavel classe, Set<Preferencia> preferencias,
			OrgaoPeticionador orgao, Set<Envolvido> envolvidos, Set<Anexo> anexos, Sigilo sigilo, TipoProcesso tipoProcesso,
			Peticionador peticionador) {
    	
    	return new Peticao(protocolo, classe, preferencias, orgao, envolvidos, anexos, sigilo, tipoProcesso, peticionador);
    }

}
