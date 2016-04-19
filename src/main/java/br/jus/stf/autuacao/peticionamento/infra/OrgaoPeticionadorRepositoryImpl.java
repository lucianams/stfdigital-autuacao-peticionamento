package br.jus.stf.autuacao.peticionamento.infra;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.peticionamento.domain.model.support.OrgaoPeticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.support.OrgaoPeticionadorRepository;
import br.jus.stf.core.shared.identidade.PessoaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 08.04.2016
 */
@Repository
public class OrgaoPeticionadorRepositoryImpl extends SimpleJpaRepository<OrgaoPeticionador, PessoaId> implements OrgaoPeticionadorRepository {

	@Autowired
    public OrgaoPeticionadorRepositoryImpl(EntityManager entityManager) {
        super(OrgaoPeticionador.class, entityManager);
    }
    
}
