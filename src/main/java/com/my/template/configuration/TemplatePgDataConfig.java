package com.my.template.configuration;

import com.my.template.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.session.jdbc.config.annotation.SpringSessionDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.TimeZone;

import static java.util.Collections.singletonMap;

/**
 * Database configuration to access template data
 *
 * Configure the PostgreSQL datasource and remove the commented annotations
 *
 * Created by Saransh Bansal on 10/08/2022.
 */
//@Profile("!mock")
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = {TemplatePgDataConfig.DOMAIN_REPO_PACKAGE},
//		entityManagerFactoryRef = "templateEntityManagerFactory",
//		transactionManagerRef = "templateTransactionManager")
//@EnableJdbcHttpSession
public class TemplatePgDataConfig {

	static final String DOMAIN_REPO_PACKAGE = "com.my.template.repository.pg";
	private static final String DOMAIN_ENTITY_PACKAGE = "com.my.template.entity.pg";
	private static final String PERSISTENCE_UNIT_NAME = "PG_TMPL";

	@Value("${template.datasource.dialect:org.hibernate.dialect.PostgreSQL10Dialect}")
	private String dialect;

	/**
	 * Custom entity manager factory bean
	 *
	 * @param builder instance of {@link EntityManagerFactoryBuilder}
	 * @param dataSource instance of {@link DataSource}
	 * @return instance of {@link LocalContainerEntityManagerFactoryBean}
	 */
	@Primary
	@Bean(name = "templateEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean templateEntityManagerFactory(
			EntityManagerFactoryBuilder builder, @Qualifier("templateDataSource") DataSource dataSource) {
		return builder
					   .dataSource(dataSource)
					   .packages(DOMAIN_ENTITY_PACKAGE)
					   .persistenceUnit(PERSISTENCE_UNIT_NAME)
					   .properties(singletonMap("hibernate.dialect", dialect))
					   .build();
	}

	@Primary
	@Bean(name = "templateTransactionManager")
	protected PlatformTransactionManager templateTransactionManager(
			@Qualifier("templateEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	/**
	 * Creates PostgreSQL data source configuration
	 *
	 * @return instance of {@link DataSource}
	 */
	@Primary
	@Bean(name = "templateDataSource")
	@SpringSessionDataSource
	@ConfigurationProperties(prefix = "template.datasource")
	public DataSource templateDataSource() {
		//Workaround to fix PostgreSQL connection issue. For some reason -Duser.timezone parameter doesn't work on PCF
		TimeZone.setDefault(
				TimeZone.getTimeZone(DateTimeUtil.DEFAULT_TIME_ZONE)
		);
		return DataSourceBuilder.create().build();
	}
}