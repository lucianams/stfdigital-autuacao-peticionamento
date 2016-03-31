package br.jus.stf.autuacao.peticionamento.infra;

import static java.time.Year.now;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.jus.stf.autuacao.peticionamento.domain.ProtocoloAdapter;
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

	@Override
	public Protocolo novoProtocolo() {
		URI uri = UriComponentsBuilder.fromUriString("http://localhost:8081/api").path("/identificadores").queryParam("categoria", String.valueOf(now().getValue())).build().toUri();
		
		IdentificacaoDto identficador = new RestTemplate().getForObject(uri, IdentificacaoDto.class);
		
    	Numero numero = new Numero(identficador.getNumero(), Integer.valueOf(identficador.getCategoria()));
    	
		uri = UriComponentsBuilder.fromUriString("http://localhost:8081/api").path("/identificadores").build().toUri();
		
    	Long identificadorId = new RestTemplate().getForObject(uri, Long.class);
    	
    	ProtocoloId protocoloId = new ProtocoloId(identificadorId);
    	
		return new Protocolo(protocoloId, numero);
	}
	
}
