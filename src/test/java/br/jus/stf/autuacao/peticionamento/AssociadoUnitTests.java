package br.jus.stf.autuacao.peticionamento;

import org.junit.Assert;
import org.junit.Test;

import br.jus.stf.autuacao.peticionamento.domain.model.identidade.Associado;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.TipoAssociado;
import br.jus.stf.core.shared.identidade.PessoaId;

public class AssociadoUnitTests {
	
	@Test
	public void criaAssociadoSemCargoValido() {
		PessoaId pessoaId = new PessoaId(1L);
		Associado associado = new Associado(1L, pessoaId, "José da Silva", TipoAssociado.GESTOR, null);
		
		
		Assert.assertNotNull("Associado não deve ser nulo.", associado);
		Assert.assertEquals("Identificadores devem ser iguais.", Long.valueOf(1), associado.toLong());
		Assert.assertEquals("Pessoas devem ser iguais.", pessoaId, associado.pessoa());
		Assert.assertEquals("Tipo de associados deve ser iguais.", TipoAssociado.GESTOR, associado.tipo());
		Assert.assertNull("Cargo/Função deve ser nulo.", associado.cargoFuncao());
	}
	
	@Test
	public void criaAssociadoComCargoValido() {
		PessoaId pessoaId = new PessoaId(1L);
		String cargoFuncao = "Procurador";
		Associado associado = new Associado(1L, pessoaId, "José da Silva", TipoAssociado.REPRESENTANTE, cargoFuncao);
		
		
		Assert.assertNotNull("Associado não deve ser nulo.", associado);
		Assert.assertEquals("Identificadores devem ser iguais.", Long.valueOf(1), associado.toLong());
		Assert.assertEquals("Pessoas devem ser iguais.", pessoaId, associado.pessoa());
		Assert.assertEquals("Tipo de associados deve ser iguais.", TipoAssociado.REPRESENTANTE, associado.tipo());
		Assert.assertEquals("Cargos/Funções devem ser iguais.", cargoFuncao, associado.cargoFuncao());
	}
	
	@Test
	public void verificaPessoaAssociadaQueNaoERepresentante() {
		PessoaId pessoaId = new PessoaId(1L);
		Associado associado = new Associado(1L, pessoaId, "José da Silva", TipoAssociado.ASSOCIADO, null);
		
		
		Assert.assertFalse("Pessoa não é associado representante.", associado.isRepresentante(pessoaId));
	}
	
	@Test
	public void verificaPessoaAssociadaQueERepresentante() {
		PessoaId pessoaId = new PessoaId(1L);
		String cargoFuncao = "Advogado";
		Associado associado = new Associado(1L, pessoaId, "José da Silva", TipoAssociado.REPRESENTANTE, cargoFuncao);
		
		
		Assert.assertTrue("Pessoa é associado representante.", associado.isRepresentante(pessoaId));
	}
	
	@Test(expected=NullPointerException.class)
	public void tentaCriarAssociadoComIdNula() {
		new Associado(null, new PessoaId(1L), "João Pedrosa", TipoAssociado.GESTOR, null);
	}
	
	@Test(expected=NullPointerException.class)
	public void tentaCriarAssociadoComPessoaNula() {
		new Associado(1L, null, "João Pedrosa", TipoAssociado.ASSOCIADO, "Estagiário");
	}
	
	@Test(expected=NullPointerException.class)
	public void tentaCriarAssociadoComTipoNulo() {
		new Associado(1L, new PessoaId(1L), "José da Silva", null, "Estagiário");
	}
	
}