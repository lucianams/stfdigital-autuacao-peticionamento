package br.jus.stf.autuacao.peticionamento;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionadorRepository;
import br.jus.stf.core.shared.identidade.PessoaId;

public class OrgaoPeticionadorRepositoryUnitTests {
	
	@Mock
	private OrgaoPeticionadorRepository orgaoRepository;
	
	private List<OrgaoPeticionador> orgaos;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		orgaos = new ArrayList<>(1);
		OrgaoPeticionador orgao = orgao();
		
		orgaos.add(orgao);
		
		
		Mockito.when(orgaoRepository.findOne(new PessoaId(1L))).thenReturn(orgao);
		Mockito.when(orgaoRepository.findAll()).thenReturn(orgaos);
		Mockito.when(orgaoRepository.findOrgaoRepresentados(true)).thenReturn(orgaos);
	}

	@Test
	public void encontraOrgaoPeloId() {
		OrgaoPeticionador orgao = orgao();
		
		Assert.assertEquals(orgao, orgaoRepository.findOne(new PessoaId(1L)));
		Mockito.verify(orgaoRepository, Mockito.times(1)).findOne(new PessoaId(1L));
	}
	
	@Test
	public void listaOrgaos() {
		Assert.assertEquals(orgaos, orgaoRepository.findAll());
		Mockito.verify(orgaoRepository, Mockito.times(1)).findAll();
	}
	
	@Test
	public void listaOrgaosRepresentados() {
		Assert.assertEquals(orgaos, orgaoRepository.findOrgaoRepresentados(true));
		Mockito.verify(orgaoRepository, Mockito.times(1)).findOrgaoRepresentados(true);
	}
	
	private OrgaoPeticionador orgao() {
		PessoaId pessoaId = new PessoaId(1L);
		String nome = "AGU";
		
		return new OrgaoPeticionador(pessoaId, nome, null);
	}
	
}
