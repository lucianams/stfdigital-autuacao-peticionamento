package br.jus.stf.autuacao.peticionamento.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.DocumentoTemporarioId;

/**
 * @author anderson.araujo
 * 
 * @since 1.0.0
 * @since 05.05.2016
 */
@Component
@FunctionalInterface
public interface AnexoAdapter {

	/**
	 * Salva os documentos e recupera os ids na ordem de envio
	 * 
	 * @param documentosTemporarios Lista de documentos temporários.
	 * @return a lista de ids dos documentos.
	 */
	List<DocumentoId> salvar(List<DocumentoTemporarioId> documentosTemporarios);
}
