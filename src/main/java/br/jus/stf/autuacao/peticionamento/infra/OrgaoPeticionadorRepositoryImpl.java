package br.jus.stf.autuacao.peticionamento.infra;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionadorRepository;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.TipoAssociado;
import br.jus.stf.core.shared.identidade.PessoaId;

/**
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 08.04.2016
 */
@Repository
public class OrgaoPeticionadorRepositoryImpl extends SimpleJpaRepository<OrgaoPeticionador, PessoaId> implements OrgaoPeticionadorRepository {

	private EntityManager entityManager;
	
	@Autowired
    public OrgaoPeticionadorRepositoryImpl(EntityManager entityManager) {
        super(OrgaoPeticionador.class, entityManager);
        this.entityManager = entityManager;
    }
	
	@Override
	public List<OrgaoPeticionador> findOrgaoRepresentados(boolean verificarPerfil) {
		String gestorCadastro = "gestor-cadastro";
		
		//TODO: Alterar para pegar informações do usuário da sessão
//		if (verificarPerfil && SecurityContextUtil.getUser().getUserDetails().getPapeis().contains(gestorCadastro)) {
		if (verificarPerfil) {
			return findAll();
		} else {
			//TODO: Alterar para pegar informações do usuário da sessão
//			PessoaId id = SecurityContextUtil.getUser().getUserDetails().getPessoaId();
			PessoaId id = new PessoaId(1L);
			TipoAssociado[] tipos = new TipoAssociado[] { TipoAssociado.GESTOR, TipoAssociado.REPRESENTANTE };
			
			return findOrgaoByTipoAssociacao(id, tipos);
		}
	}
	
	private List<OrgaoPeticionador> findOrgaoByTipoAssociacao(PessoaId id, TipoAssociado... tipos){
		TypedQuery<OrgaoPeticionador> query = entityManager.createQuery("SELECT orgao FROM OrgaoPeticionador orgao INNER JOIN orgao.associados asso WHERE asso.tipo IN :tipos AND asso.pessoa = :id ORDER BY orgao.nome", OrgaoPeticionador.class);
		
		query.setParameter("tipos", Arrays.asList(tipos));
		query.setParameter("id", id);
		
		return query.getResultList();
	}
    
}
