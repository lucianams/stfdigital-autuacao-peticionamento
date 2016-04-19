package br.jus.stf.autuacao.peticionamento.infra;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.peticionamento.domain.model.support.TipoAnexo;
import br.jus.stf.autuacao.peticionamento.domain.model.support.TipoAnexoRepository;
import br.jus.stf.core.shared.documento.TipoDocumentoId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 07.04.2016
 */
@Repository
public class TipoAnexoRepositoryImpl extends SimpleJpaRepository<TipoAnexo, TipoDocumentoId> implements TipoAnexoRepository {

	@Autowired
    public TipoAnexoRepositoryImpl(EntityManager entityManager) {
        super(TipoAnexo.class, entityManager);
    }
    
}
