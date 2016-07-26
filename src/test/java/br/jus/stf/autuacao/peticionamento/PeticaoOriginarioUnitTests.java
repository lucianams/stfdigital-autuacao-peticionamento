package br.jus.stf.autuacao.peticionamento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.jus.stf.autuacao.peticionamento.domain.model.Anexo;
import br.jus.stf.autuacao.peticionamento.domain.model.Envolvido;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticao;
import br.jus.stf.autuacao.peticionamento.domain.model.Peticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.documento.TipoAnexo;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.Associado;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.OrgaoPeticionador;
import br.jus.stf.autuacao.peticionamento.domain.model.identidade.TipoAssociado;
import br.jus.stf.autuacao.peticionamento.domain.model.suportejudicial.ClassePeticionavel;
import br.jus.stf.autuacao.peticionamento.domain.model.suportejudicial.Preferencia;
import br.jus.stf.core.shared.classe.ClasseId;
import br.jus.stf.core.shared.documento.DocumentoId;
import br.jus.stf.core.shared.documento.TipoDocumentoId;
import br.jus.stf.core.shared.identidade.PessoaId;
import br.jus.stf.core.shared.preferencia.PreferenciaId;
import br.jus.stf.core.shared.processo.Polo;
import br.jus.stf.core.shared.processo.Sigilo;
import br.jus.stf.core.shared.processo.TipoProcesso;
import br.jus.stf.core.shared.protocolo.Numero;
import br.jus.stf.core.shared.protocolo.Protocolo;
import br.jus.stf.core.shared.protocolo.ProtocoloId;;

/**
 * Valida a API de envio de petições.
 * 
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 12.07.2016
 */
public class PeticaoOriginarioUnitTests {
	
	@Test
    public void registrarUmaPeticao() {
		Protocolo protocolo = new Protocolo(new ProtocoloId(1L), new Numero(1L, 2016));
		Peticionador peticionador = new Peticionador("peticionador", new PessoaId(1L));
		ClassePeticionavel classe = new ClassePeticionavel(new ClasseId("ADO"), "ADO", TipoProcesso.ORIGINARIO, null);
		Set<Envolvido> envolvidos = new HashSet<>(1);
		
		envolvidos.add(new Envolvido("Maria", Polo.ATIVO, new PessoaId(6L)));
		
		Peticao peticaoValida = peticao(protocolo, classe, envolvidos, peticionador, null);
        
        assertNotNull("Petição não pode ser nula.", peticaoValida);
        assertEquals("ProtocoloId deve ser igual a 1.", protocolo.identity(), peticaoValida.identity());
        assertEquals("Tipo do processo deve ser igual a ORIGINARIO.", TipoProcesso.ORIGINARIO, peticaoValida.tipoProcesso());
    }

	@Test
    public void registrarUmaPeticaoComRepresentacao() {
		Protocolo protocolo = new Protocolo(new ProtocoloId(2L), new Numero(2L, 2016));
		ClassePeticionavel classe = new ClassePeticionavel(new ClasseId("ADO"), "ADO", TipoProcesso.ORIGINARIO, null);
		Set<Envolvido> envolvidos = new HashSet<>(1);
		
		envolvidos.add(new Envolvido("Maria", Polo.ATIVO, new PessoaId(6L)));
		
		Set<Associado> associados = new HashSet<>(1);
		PessoaId pessoaId = new PessoaId(1L);
		
		associados.add(new Associado(1L, pessoaId, "representante", TipoAssociado.REPRESENTANTE, null));
		
		OrgaoPeticionador orgao = new OrgaoPeticionador(new PessoaId(5L), "AGU", associados);
		Peticionador peticionador = new Peticionador("representante", pessoaId);
		Peticao peticaoValida = peticao(protocolo, classe, envolvidos, peticionador, orgao);
        
        assertNotNull("Petição não pode ser nula.", peticaoValida);
        assertEquals("ProtocoloId deve ser igual a 2.", protocolo.identity(), peticaoValida.identity());
        assertEquals("Tipo do processo deve ser igual a ORIGINARIO.", TipoProcesso.ORIGINARIO, peticaoValida.tipoProcesso());
    }

	@Test(expected = NullPointerException.class)
    public void tentaRegistrarUmaPeticaoInvalida() {
		new Peticao(null, null, null, null, null, null, Sigilo.PUBLICO, TipoProcesso.ORIGINARIO, null);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void tentaRegistrarUmaPeticaoEnvolvidosInvalidos() {
		Protocolo protocolo = new Protocolo(new ProtocoloId(1L), new Numero(1L, 2016));
		Peticionador peticionador = new Peticionador("peticionador", new PessoaId(1L));
		ClassePeticionavel classe = new ClassePeticionavel(new ClasseId("ADO"), "ADO", TipoProcesso.ORIGINARIO, null);
		Set<Envolvido> envolvidos = new HashSet<>(1);
		
		envolvidos.add(new Envolvido("Maria", Polo.PASSIVO, new PessoaId(6L)));
		
		Set<Anexo> anexos = new HashSet<>(1);
		
		anexos.add(new Anexo(new TipoAnexo(new TipoDocumentoId(1L), "Petição inicial"), new DocumentoId(2L)));
		
		peticao(protocolo, classe, envolvidos, peticionador, null);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void tentaRegistrarUmaPeticaoComRepresentacaoInvalida() {
		Protocolo protocolo = new Protocolo(new ProtocoloId(2L), new Numero(2L, 2016));
		ClassePeticionavel classe = new ClassePeticionavel(new ClasseId("ADO"), "ADO", TipoProcesso.ORIGINARIO, null);
		Set<Envolvido> envolvidos = new HashSet<>(1);
		
		envolvidos.add(new Envolvido("Maria", Polo.ATIVO, new PessoaId(6L)));
		Set<Associado> associados = new HashSet<>(1);
		
		associados.add(new Associado(1L, new PessoaId(1L), "representante", TipoAssociado.REPRESENTANTE, null));
		
		OrgaoPeticionador orgao = new OrgaoPeticionador(new PessoaId(5L), "AGU", associados);
		Peticionador peticionador = new Peticionador("representante", new PessoaId(2L));

		peticao(protocolo, classe, envolvidos, peticionador, orgao);;
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void tentaRegistrarUmaPeticaoComClasseTipoProcessoIncompativeis() {
		Protocolo protocolo = new Protocolo(new ProtocoloId(1L), new Numero(1L, 2016));
		Peticionador peticionador = new Peticionador("peticionador", new PessoaId(1L));
		ClassePeticionavel classe = new ClassePeticionavel(new ClasseId("RE"), "RE", TipoProcesso.RECURSAL, null);
		Set<Envolvido> envolvidos = new HashSet<>(1);
		
		envolvidos.add(new Envolvido("Maria", Polo.ATIVO, new PessoaId(6L)));
		
		Set<Anexo> anexos = new HashSet<>(1);
		
		anexos.add(new Anexo(new TipoAnexo(new TipoDocumentoId(1L), "Petição inicial"), new DocumentoId(2L)));
		
		peticao(protocolo, classe, envolvidos, peticionador, null);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void tentaRegistrarUmaPeticaoComClassePreferenciasIncompativeis() {
		Protocolo protocolo = new Protocolo(new ProtocoloId(1L), new Numero(1L, 2016));
		Peticionador peticionador = new Peticionador("peticionador", new PessoaId(1L));
		Set<Preferencia> preferenciasClasse = new HashSet<>(1);
		
		preferenciasClasse.add(new Preferencia(new PreferenciaId(1L), "Medida Cautelar"));
		ClassePeticionavel classe = new ClassePeticionavel(new ClasseId("ADI"), "ADI", TipoProcesso.ORIGINARIO, preferenciasClasse);
		Set<Envolvido> envolvidos = new HashSet<>(1);
		
		envolvidos.add(new Envolvido("Maria", Polo.ATIVO, new PessoaId(6L)));
		
		Set<Anexo> anexos = new HashSet<>(1);
		
		anexos.add(new Anexo(new TipoAnexo(new TipoDocumentoId(1L), "Petição inicial"), new DocumentoId(2L)));
		
		Set<Preferencia> preferenciasPeticao = new HashSet<>(1);
		
		preferenciasPeticao.add(new Preferencia(new PreferenciaId(4L), "Idoso"));
		
		new Peticao(protocolo, classe, preferenciasPeticao, null, envolvidos, anexos, Sigilo.PUBLICO, TipoProcesso.ORIGINARIO, peticionador);
    }
	
	private Peticao peticao(Protocolo protocolo, ClassePeticionavel classe, Set<Envolvido> envolvidos, Peticionador peticionador, OrgaoPeticionador orgao) {
		Set<Anexo> anexos = new HashSet<>(1);
		
		anexos.add(new Anexo(new TipoAnexo(new TipoDocumentoId(1L), "Petição inicial"), new DocumentoId(2L)));
		
		return new Peticao(protocolo, classe, null, orgao, envolvidos, anexos, Sigilo.PUBLICO, TipoProcesso.ORIGINARIO, peticionador);
	}
    
}