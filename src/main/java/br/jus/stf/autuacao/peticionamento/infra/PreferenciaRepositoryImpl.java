package br.jus.stf.autuacao.peticionamento.infra;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.peticionamento.domain.model.support.Preferencia;
import br.jus.stf.autuacao.peticionamento.domain.model.support.PreferenciaRepository;
import br.jus.stf.core.shared.preferencia.PreferenciaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 06.04.2016
 */
@Repository
public class PreferenciaRepositoryImpl extends SimpleJpaRepository<Preferencia, PreferenciaId> implements PreferenciaRepository {

	@Autowired
    public PreferenciaRepositoryImpl(EntityManager entityManager) {
        super(Preferencia.class, entityManager);
    }
    
}
