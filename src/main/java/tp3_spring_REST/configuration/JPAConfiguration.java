package tp3_spring_REST.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan
@PropertySource("classpath:application.properties")
public class JPAConfiguration {
	@Autowired
	Environment environment;
	
	@Bean(name = "dataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));

		return dataSource;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean retour = new LocalContainerEntityManagerFactoryBean();
		
		//conf : dialect
		retour.setJpaDialect(new HibernateJpaDialect());
		
		//conf : adapter
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(environment.getRequiredProperty("hibernate.show_sql", Boolean.class));
		retour.setJpaVendorAdapter(adapter);
		
		//conf : packagesToScan
		retour.setPackagesToScan("tp3_spring_REST");
		
		//conf : jpa properties
		retour.getJpaPropertyMap().put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		retour.getJpaPropertyMap().put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql", Boolean.class));
		retour.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
		retour.getJpaPropertyMap().put("hibernate.cache.use_second_level_cache", environment.getRequiredProperty("hibernate.cache.use_second_level_cache", Boolean.class));
		
		retour.setDataSource(this.dataSource());
		
		return retour;
	}
	
	@Bean
	public PlatformTransactionManager txManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
	
	

}
