package br.jus.stf.autuacao.peticionamento.domain.model.documento;

import java.util.List;

import br.jus.stf.core.shared.documento.TipoDocumentoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 07.04.2016
 */
public interface TipoAnexoRepository {

	List<TipoAnexo> findAll();
	
	TipoAnexo findOne(TipoDocumentoId id);

}
