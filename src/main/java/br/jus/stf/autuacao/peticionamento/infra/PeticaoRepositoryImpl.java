package br.jus.stf.autuacao.peticionamento.infra;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.peticionamento.domain.model.Peticao;
import br.jus.stf.autuacao.peticionamento.domain.model.PeticaoRepository;
import br.jus.stf.core.shared.protocolo.ProtocoloId;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 26.02.2016
 */
@Repository
public class PeticaoRepositoryImpl extends SimpleJpaRepository<Peticao, ProtocoloId> implements PeticaoRepository {

	@Autowired
    public PeticaoRepositoryImpl(EntityManager entityManager) {
        super(Peticao.class, entityManager);
    }
    
}
