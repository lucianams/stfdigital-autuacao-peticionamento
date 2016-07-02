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
		IdentificacaoDto identficador = protocoloRestClient.identificador(String.valueOf(now().getValue()));
		
    	Numero numero = new Numero(identficador.getNumero(), Integer.valueOf(identficador.getCategoria()));
    	
    	Long identificadorId = protocoloRestClient.identificador();
    	
    	ProtocoloId protocoloId = new ProtocoloId(identificadorId);
    	
		return new Protocolo(protocoloId, numero);
	}
	
}
