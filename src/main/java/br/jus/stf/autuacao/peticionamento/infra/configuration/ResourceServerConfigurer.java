package br.jus.stf.autuacao.peticionamento.infra.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 18.12.2015
 */
@Configuration
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/bundle.js*", "/maps/bundle.js.map*", "/info").permitAll().anyRequest().authenticated();
	}
	
}
