package com.roy.shortener.base.configurations.flyway;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FlywayConfiguration {

    public final DataSource dataSource;

    @Bean("flywayForShortener")
    public Flyway flywayForJpa() {
        Flyway flyway = Flyway.configure().baselineOnMigrate(Boolean.TRUE).dataSource(this.dataSource).locations("database/migration").load();
        return flyway;
    }

    static class JpaFlywayMigrationInitializer extends FlywayMigrationInitializer {

        Flyway[] flyways;

        public JpaFlywayMigrationInitializer(Flyway flyway, Flyway... flyways) {
            super(flyway);
            this.flyways = flyways;
        }
    }

    @Bean
    public FlywayMigrationInitializer initializeDBs(@Qualifier("flywayForShortener") Flyway flywayForShortener) {
        return new JpaFlywayMigrationInitializer(flywayForShortener);
    }
}
