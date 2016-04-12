package br.jus.stf.autuacao.peticionamento.infra;

import static java.time.Year.now;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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

    @Autowired
    private DiscoveryClient discoveryClient;
    
	@Override
	public Protocolo novoProtocolo() {
		URI servicesUri = discoveryClient.getInstances("services").get(0).getUri();
		
		URI uri = UriComponentsBuilder.fromUri(servicesUri).path("/api/identificadores").queryParam("categoria", String.valueOf(now().getValue())).build().toUri();
		
		IdentificacaoDto identficador = new RestTemplate().getForObject(uri, IdentificacaoDto.class);
		
    	Numero numero = new Numero(identficador.getNumero(), Integer.valueOf(identficador.getCategoria()));
    	
		uri = UriComponentsBuilder.fromUri(servicesUri).path("/api/identificadores").build().toUri();
		
    	Long identificadorId = new RestTemplate().getForObject(uri, Long.class);
    	
    	ProtocoloId protocoloId = new ProtocoloId(identificadorId);
    	
		return new Protocolo(protocoloId, numero);
	}
	
}
