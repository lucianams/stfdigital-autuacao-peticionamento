package br.jus.stf.autuacao.peticionamento.infra.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 18.12.2015
 */
@Configuration
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
	
	@Autowired(required = false)
    @Qualifier("oauth2StatelessSecurityContext")
    private Boolean stateless = Boolean.TRUE;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(stateless);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/bundle.js*", "/maps/bundle.js.map*", "/manage/**").permitAll().anyRequest().authenticated();
	}
	
}
