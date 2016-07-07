package br.jus.stf.autuacao.peticionamento.infra;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.peticionamento.domain.model.classe.ClassePeticionavel;
import br.jus.stf.autuacao.peticionamento.domain.model.classe.ClassePeticionavelRepository;
import br.jus.stf.core.shared.classe.ClasseId;
import br.jus.stf.core.shared.processo.TipoProcesso;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 06.04.2016
 */
@Repository
public class ClassePeticionavelRepositoryImpl extends SimpleJpaRepository<ClassePeticionavel, ClasseId> implements ClassePeticionavelRepository {

	private EntityManager entityManager;
	
	/**
	 * @param entityManager
	 */
	@Autowired
    public ClassePeticionavelRepositoryImpl(EntityManager entityManager) {
        super(ClassePeticionavel.class, entityManager);
        this.entityManager = entityManager;
    }
	
	@Override
	public List<ClassePeticionavel> findByTipo(TipoProcesso tipo) {
		TypedQuery<ClassePeticionavel> query = entityManager.createQuery("FROM ClassePeticionavel classe WHERE classe.tipo = :tipo", ClassePeticionavel.class);
		
		query.setParameter("tipo", tipo);
		
		return query.getResultList();
	}
    
}
