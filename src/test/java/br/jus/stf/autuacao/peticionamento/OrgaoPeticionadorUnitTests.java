package br.jus.stf.autuacao.peticionamento;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import br.jus.stf.autuacao.peticionamento.domain.model.identidade.Associado;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.TipoAssociado;
import br.jus.stf.core.shared.identidade.PessoaId;

public class OrgaoPeticionadorUnitTests {
	
	@Test
	public void criaOrgaoSemAssociadosValido() {
		PessoaId id = new PessoaId(1L);
		String nome = "AGU";
		OrgaoPeticionador orgao = new OrgaoPeticionador(id, nome, null);
		
		Assert.assertNotNull("Órgão não pode ser nulo.", orgao);
		Assert.assertEquals("Identidades devem ser iguais.", id, orgao.identity());
		Assert.assertEquals("Nomes devem ser iguais.", nome, orgao.nome());
		Assert.assertTrue("Órgão não possui associados.", orgao.associados().isEmpty());
	}
	
	@Test
	public void criaOrgaoComAssociadosValido() {
		PessoaId id = new PessoaId(1L);
		String nome = "AGU";
		Set<Associado> associados = new HashSet<>(Arrays
				.asList(new Associado(1L, new PessoaId(1L), "José Santos", TipoAssociado.ASSOCIADO, "Estagiário")));
		OrgaoPeticionador orgao = new OrgaoPeticionador(id, nome, associados);
		
		Assert.assertNotNull("Órgão não pode ser nulo.", orgao);
		Assert.assertEquals("Identidades devem ser iguais.", id, orgao.identity());
		Assert.assertEquals("Nomes devem ser iguais.", nome, orgao.nome());
		Assert.assertEquals("Órgão deve ter um associado.", associados, orgao.associados());
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarOrgaoComIdNulo() {
		new OrgaoPeticionador(null, "AGU", null);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarOrgaoComNomeNulo() {
		new OrgaoPeticionador(new PessoaId(1L), null, null);
	}
	
	@Test
	public void verificaPessoaQueNaoRepresentaOrgao() {
		PessoaId pessoaId = new PessoaId(1L);
		Associado associado = new Associado(1L, pessoaId, "José Santos", TipoAssociado.ASSOCIADO, "Estagiário");
		OrgaoPeticionador orgao = orgao(new HashSet<Associado>(Arrays.asList(associado)));
		
		Assert.assertFalse("Pessoa não deve ser representante do Órgão.", orgao.isRepresentadoPor(pessoaId));
	}
	
	@Test
	public void verificaPessoaQueRepresentaOrgao() {
		PessoaId pessoaId = new PessoaId(1L);
		Associado associado = new Associado(1L, pessoaId, "José Matos", TipoAssociado.REPRESENTANTE, "Advogado");
		OrgaoPeticionador orgao = orgao(new HashSet<Associado>(Arrays.asList(associado)));
		
		Assert.assertTrue("Pessoa deve ser representa do Órgão.", orgao.isRepresentadoPor(pessoaId));
	}
	
	private OrgaoPeticionador orgao(Set<Associado> associados) {
		PessoaId id = new PessoaId(1L);
		String nome = "AGU";
		
		return new OrgaoPeticionador(id, nome, associados);
	}

}