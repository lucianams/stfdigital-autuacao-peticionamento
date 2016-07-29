package br.jus.stf.autuacao.peticionamento.infra;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@DependsOn("runtimeServiceBean")
@Profile("e2e")
public class E2ETestsDataLoader {

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	public void init() throws SQLException {
		loadDataTests("cadastrarAssociado.sql");
	}

	private void loadDataTests(String... scriptsSql) throws SQLException {
		Connection connection = null;
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

		for (String scriptSql : scriptsSql) {
			populator.addScript(new ClassPathResource("/db/tests/" + scriptSql));
		}

		try {
			connection = DataSourceUtils.getConnection(dataSource);
			populator.populate(connection);
		} finally {
			if (connection != null) {
				DataSourceUtils.releaseConnection(connection, dataSource);
			}
		}
	}

}
