package br.jus.stf.autuacao.peticionamento.infra;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 29.03.2016
 */
@Configuration
public class PersistenceConfiguration {
	
	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(org.h2.Driver.class);
		dataSource.setUrl("jdbc:h2:" + System.getProperty("user.home") + "/.stfdigital/database;MODE=Oracle;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}
	
}
