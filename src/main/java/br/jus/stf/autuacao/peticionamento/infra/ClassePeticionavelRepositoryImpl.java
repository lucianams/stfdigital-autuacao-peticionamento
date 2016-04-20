package br.jus.stf.autuacao.peticionamento.infra;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.peticionamento.domain.model.classe.ClassePeticionavel;
import br.jus.stf.autuacao.peticionamento.domain.model.classe.ClassePeticionavelRepository;
import br.jus.stf.core.shared.classe.ClasseId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 06.04.2016
 */
@Repository
public class ClassePeticionavelRepositoryImpl extends SimpleJpaRepository<ClassePeticionavel, ClasseId> implements ClassePeticionavelRepository {

	@Autowired
    public ClassePeticionavelRepositoryImpl(EntityManager entityManager) {
        super(ClassePeticionavel.class, entityManager);
    }
    
}
