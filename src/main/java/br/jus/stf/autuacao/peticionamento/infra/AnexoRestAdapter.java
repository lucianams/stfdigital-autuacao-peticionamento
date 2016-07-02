package br.jus.stf.autuacao.peticionamento.infra;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.jus.stf.autuacao.peticionamento.domain.AnexoAdapter;
import br.jus.stf.autuacao.peticionamento.infra.client.DocumentoRestClient;
import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.DocumentoTemporarioId;

/**
 * @author Anderson Araújo
 * 
 * @since 1.0.0
 * @since 05.05.2016
 */
@Component
public class AnexoRestAdapter implements AnexoAdapter {

	@Autowired
    private DocumentoRestClient documentoRestClient;
	
	@Override
	public List<DocumentoId> salvar(List<DocumentoTemporarioId> documentosTemporarios) {
		MultiValueMap<String, String> command = new LinkedMultiValueMap<>();
		documentosTemporarios.forEach(doc -> command.add("idsDocumentosTemporarios", doc.toString()));
		
		List<Map<String, Object>> documentos = documentoRestClient.documentos(command);
		
		return documentos.stream().map(doc -> new DocumentoId(((Integer) doc.get("documentoId")).longValue())).collect(Collectors.toList());
	}

}
