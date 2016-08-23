package br.jus.stf.autuacao.peticionamento.infra.configuration;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

import br.jus.stf.core.framework.stream.StreamConfigurerSupport;
import br.jus.stf.core.shared.eventos.EnvolvidoRegistrado;
import br.jus.stf.core.shared.eventos.PeticaoRegistrada;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 18.08.2016
 */
@EnableBinding(StreamConfigurer.Channels.class)
public class StreamConfigurer extends StreamConfigurerSupport {
	
	/**
	 * @see br.jus.stf.core.framework.stream.StreamConfigurerSupport#serviceSchema()
	 */
	@Override
	protected String serviceSchema() {
		return "peticionamento";
	}
	
	/**
	 * Configuração dos canais que serão usados pelo serviço de peticionamento
	 * para publicação de eventos de domínio.
	 * 
	 * @author Rodrigo Barreiros
	 * 
	 * @since 1.0.0
	 * @since 18.08.2016
	 */
	public interface Channels {

		/**
		 * O canal que será usado para publicação de eventos do tipo {@link PeticaoRegistrada}.
		 * 
		 * @return o canal para as petições registradas
		 */
		@Output(PeticaoRegistrada.EVENT_KEY)
		MessageChannel peticaoRegistrada();

		/**
		 * O canal que será usado para publicação de eventos do tipo {@link EnvolvidoRegistrado}.
		 * 
		 * @return o canal para os envolvidos registrados
		 */
		@Output(EnvolvidoRegistrado.EVENT_KEY)
		MessageChannel envolvidoRegistrado();

	}

}
