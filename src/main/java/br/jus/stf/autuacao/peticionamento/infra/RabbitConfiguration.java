package br.jus.stf.autuacao.peticionamento.infra;

import java.util.Arrays;
import java.util.List;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 01.03.2016
 */
@Configuration
@EnableRabbit
public class RabbitConfiguration {

	public static final String PETICAO_REGISTRADA_EXCHANGE = "autuacao.peticao.registrada";
	
	public static final String PETICAO_REGISTRADA_ROUTE = "autuacao.peticao.registrada";

	public static final String PARTE_REGISTRADA_QUEUE = "autuacao.peticao.envolvido.registrado";

	@Bean
	public ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory("localhost");
	}
	
    /**
     * Esse listener específico é utilizado para viabilizar o consumo de mensagens de forma assíncrona usando
     * a anotação {@link RabbitListener}. Veja que ele é configurado para usar uma conversor JSON, que é
     * o protocolo recomendado para troca de mensagens.
     * 
     * @return o listener para viabilizar o consumo de mensagens assíncronas
     * 
     * @see http://docs.spring.io/spring-amqp/reference/html/_reference.html#async-annotation-driven
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
    
    /**
     * Template usado para envio de mensagens. Veja que ele também é configurado para usar uma conversor JSON, 
     * que é o protocolo recomendado para troca de mensagens.
     * 
     * @return o template usado para enviar mensagens
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
	
	/**
	 * {@link Jackson2JsonMessageConverter} é conversor que usa a versão 2.x do Jackson. Não confudir 
	 * com {@link JsonMessageConverter}, que usa a versão 1.x do Jackson.
	 * 
	 * @return o conversor padrão JSON
	 */
	@Bean
	public Jackson2JsonMessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	/**
	 * Usamos um {@link TopicExchange} para conectar a fila de saída (publisher) a cada fila de chegada (subscribers).
	 * 
	 * @return o conectar entre filas
	 */
	@Bean
	TopicExchange exchange() {
		return new TopicExchange(PETICAO_REGISTRADA_EXCHANGE);
	}
	
    /**
     * Cria todas as filas necessárias. Tanto as usadas pelos publishers, quanta aqueles usadas pelos subscribers.
     * 
     * @return todas as filas necessárias
     */
    @Bean
    public List<Queue> queues() {
    	return Arrays.asList(
	    	new Queue(PARTE_REGISTRADA_QUEUE, false)
    	);
    }

}