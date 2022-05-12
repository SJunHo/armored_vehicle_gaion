package kr.gaion.railroad2.database;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DatabaseModule {
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, DatabaseConfiguration configuration) {
    LocalContainerEntityManagerFactoryBean em
        = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("kr.gaion.railroad2");

    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaProperties(configuration.getProperties());

    return em;
  }

  @Bean
  DataSource getDataSource(DatabaseConfiguration configuration) {
    var builder = DataSourceBuilder.create();
    builder.driverClassName(configuration.getDriverClass());
    builder.url(configuration.getUrl());
    builder.password(configuration.getPassword());
    builder.username(configuration.getUser());
    return builder.build();
  }
}