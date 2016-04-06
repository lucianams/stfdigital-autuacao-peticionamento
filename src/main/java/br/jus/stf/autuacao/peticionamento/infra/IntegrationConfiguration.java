package br.jus.stf.autuacao.peticionamento.infra;

import static br.jus.stf.autuacao.peticionamento.infra.RabbitConfiguration.PARTE_REGISTRADA_QUEUE;
import static br.jus.stf.autuacao.peticionamento.infra.RabbitConfiguration.PETICAO_REGISTRADA_EXCHANGE;
import static br.jus.stf.autuacao.peticionamento.infra.RabbitConfiguration.PETICAO_REGISTRADA_ROUTE;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

import com.google.common.collect.ImmutableMap;

import br.jus.stf.core.shared.eventos.EnvolvidoRegistrado;
import br.jus.stf.core.shared.eventos.PeticaoRegistrada;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.03.2016
 */
@Configuration
@EnableIntegration
public class IntegrationConfiguration {

	private static final String EVENTOS_QUERY = "SELECT * FROM peticionamento.evento WHERE tip_status = 1";

	private static final String EVENTOS_UPDATE = "UPDATE peticionamento.evento SET tip_status = 2 WHERE seq_evento IN (:seq_evento)";

	private static Map<String, DomainEventMessage> queues = new HashMap<>();
	
	static {
		queues.put(PeticaoRegistrada.class.getSimpleName(), new DomainEventMessage(PeticaoRegistrada.class, PETICAO_REGISTRADA_EXCHANGE, PETICAO_REGISTRADA_ROUTE));
		queues.put(EnvolvidoRegistrado.class.getSimpleName(), new DomainEventMessage(EnvolvidoRegistrado.class, PARTE_REGISTRADA_QUEUE));
	}

	@Autowired
	private DataSource dataSource;

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Bean
	public IntegrationFlow flow() {
		return IntegrationFlows.from(eventosDatatable(), c -> c.poller(Pollers.fixedRate(1000).maxMessagesPerPoll(1)))
			.split()
			.transform(new DomainEventTransformer())
			.handle(Amqp.outboundAdapter(rabbitTemplate).routingKeyExpression("headers['routingKey']").exchangeNameExpression("headers['exchange']"))
			.get();
	}

	@Bean
	@DependsOn("flyway")
	public MessageSource<Object> eventosDatatable() {
		JdbcPollingChannelAdapter adapter = new JdbcPollingChannelAdapter(dataSource, EVENTOS_QUERY);
	    adapter.setUpdateSql(EVENTOS_UPDATE);
	    return adapter;
	}
	
	/**
	 * Transformer usado para converter a mensagem original (um mapa com os atritutos do evento) em uma
	 * mensagem representando o evento que será publicado em sua fila correspondente.
	 * 
	 * <p>É provável que seja possível fazer a transformação abaixo via DSL, mas a API do Spring
	 * Integration ainda não é completa o suficiente.
	 *  
	 * @author Rodrigo Barreiros
	 *
	 * @see 1.0.0
	 * @since 11.03.2016
	 */
	@SuppressWarnings("unchecked")
	private class DomainEventTransformer extends JsonToObjectTransformer {
		
		@Override
		protected Object doTransform(Message<?> originalMessage) throws Exception {
			DomainEventMessage message = queues.get(((Map<String, String>) originalMessage.getPayload()).get("NOM_EVENTO"));
			
			Map<String, Object> headers = ImmutableMap.of("routingKey", message.routingKey(), "exchange", message.exchange());
			
			String payload = ((Map<String, String>) originalMessage.getPayload()).get("BIN_DETALHE");
			
			GenericMessage<String> genericMessage = new GenericMessage<>(payload, new MessageHeaders(headers));
			
			JsonToObjectTransformer transformer = new JsonToObjectTransformer(message.eventClass());
			
			return transformer.transform(genericMessage);
		}
		
	}
	
	private static class DomainEventMessage {
		
		private Class<?> eventClass;
		private String exchange;
		private String routingKey;
		
		public DomainEventMessage(Class<?> eventClass, String exchange, String routingKey) {
			this.eventClass = eventClass;
			this.exchange = exchange;
			this.routingKey = routingKey;
		}

		public DomainEventMessage(Class<?> eventClass, String routingKey) {
			this.eventClass = eventClass;
			this.routingKey = routingKey;
			this.exchange = "";
		}
		
		public Class<?> eventClass() {
			return eventClass;
		}

		public String exchange() {
			return exchange;
		}
		
		public String routingKey() {
			return routingKey;
		}
		
	}
	
}