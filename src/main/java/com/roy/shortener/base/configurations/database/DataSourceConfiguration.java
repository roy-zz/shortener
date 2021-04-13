package com.roy.shortener.base.configurations.database;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.H2Dialect;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@EnableJpaRepositories(basePackages = {"com.roy.shortener"})
@EnableTransactionManagement
public class DataSourceConfiguration extends HikariConfig {

  @Bean(name = "dataSource")
  public HikariDataSource dataSource() throws SQLException {
    return new HikariDataSource(this);
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(HikariDataSource dataSource) {
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setDatabase(Database.H2);
    vendorAdapter.setDatabasePlatform(H2Dialect.class.getName());
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setJpaProperties(additionalJpaProperties());
    factory.setDataSource(dataSource);
    factory.setPackagesToScan("com.roy.shortener.base.domains");

    return factory;
  }

  private Properties additionalJpaProperties() {
    Properties properties = new Properties();
    properties.setProperty(AvailableSettings.PHYSICAL_NAMING_STRATEGY,
        "com.roy.shortener.base.configurations.jpa.HibernatePhysicalNamingStrategy");
    properties.setProperty(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS, "false");

    return properties;
  }

  @Bean
  @Primary
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory);
    return txManager;
  }

  @Bean("jdbcTemplate")
  @Primary
  @Scope("prototype")
  public JdbcTemplate jdbcTemplate() throws SQLException {
    return new JdbcTemplate(this.dataSource());
  }
}
