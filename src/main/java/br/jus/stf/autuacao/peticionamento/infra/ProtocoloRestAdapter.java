package br.jus.stf.autuacao.peticionamento.infra;

import static java.time.Year.now;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.jus.stf.autuacao.peticionamento.domain.ProtocoloAdapter;
import br.jus.stf.autuacao.peticionamento.infra.client.ProtocoloRestClient;
import br.jus.stf.core.shared.protocolo.Numero;
import br.jus.stf.core.shared.protocolo.Protocolo;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.02.2016
 */
@Component
public class ProtocoloRestAdapter implements ProtocoloAdapter {

    @Autowired
    private ProtocoloRestClient protocoloRestClient;
    
	@Override
	public Protocolo novoProtocolo() {
		return new Protocolo(novoProtocoloId(), novoNumero());
	}

	private ProtocoloId novoProtocoloId() {
		Long identificadorId = protocoloRestClient.identificador();
    	return new ProtocoloId(identificadorId);
	}

	private Numero novoNumero() {
		Integer ano = now().getValue();
		Long identificador = protocoloRestClient.identificador(ano.toString());
    	return new Numero(identificador, ano);
	}
	
}
