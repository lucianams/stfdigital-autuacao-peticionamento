package br.jus.stf.autuacao.peticionamento.infra.configuration;

import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import feign.RequestInterceptor;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 30.06.2016
 */
@EnableOAuth2Client
@Configuration
public class FeignClientsConfigurer {

	@Bean
	public RequestInterceptor feignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext) {
		return new OAuth2FeignRequestInterceptor(oAuth2ClientContext, new ClientCredentialsResourceDetails());
	}

}
