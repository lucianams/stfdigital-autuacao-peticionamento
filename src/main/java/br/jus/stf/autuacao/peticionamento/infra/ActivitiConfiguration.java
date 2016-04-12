package br.jus.stf.autuacao.peticionamento.infra;

import java.io.IOException;

import javax.sql.DataSource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Lucas Mariano
 * @author Rafael Alencar
 * 
 * @since 1.0.0
 * @since 29.06.2015
 */
@Configuration
public class ActivitiConfiguration {
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Bean
	public SpringProcessEngineConfiguration processEngineConfiguration() throws Exception {	
		SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
		
		configuration.setDataSource(dataSourceActiviti());
		configuration.setTransactionManager(transactionManager);
		configuration.setDatabaseSchemaUpdate("true");
		configuration.setJobExecutorActivate(false);
		configuration.setDeploymentResources(getResources("classpath*:/processes/*.bpmn20.xml"));
		configuration.setDeploymentMode("single-resource");
		return configuration;
	}

	private Resource[] getResources(String locationPattern) throws IOException {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		return resolver.getResources(locationPattern);
	}

	@Bean
	public ProcessEngineFactoryBean processEngine() throws Exception {
		ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
		factoryBean.setProcessEngineConfiguration(processEngineConfiguration());
		return factoryBean;
	}
	
	@Bean
	public RuntimeService runtimeService() throws Exception {
		return processEngine().getObject().getRuntimeService();
	}
	
	@Bean
	public RepositoryService repositoryService() throws Exception {
		return processEngine().getObject().getRepositoryService();
	}
	
	@Bean
	public HistoryService historyService() throws Exception {
		return processEngine().getObject().getHistoryService();
	}
	
	@Bean
	public TaskService taskService() throws Exception {
		return processEngine().getObject().getTaskService();
	}
	
	@Bean
	public ManagementService managementService() throws Exception {
		return processEngine().getObject().getManagementService();
	}	

	@Bean
	public IdentityService identityService() throws Exception {
		return processEngine().getObject().getIdentityService();
	}
	
	private DataSource dataSourceActiviti() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		
		dataSource.setDriverClass(org.h2.Driver.class);
		dataSource.setUrl(connectionUrl());
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}

	private String connectionUrl() {
		return "jdbc:h2:tcp://localhost:8082/mem:stfdigitalactiviti;MODE=Oracle;DB_CLOSE_DELAY=-1";
	}

}