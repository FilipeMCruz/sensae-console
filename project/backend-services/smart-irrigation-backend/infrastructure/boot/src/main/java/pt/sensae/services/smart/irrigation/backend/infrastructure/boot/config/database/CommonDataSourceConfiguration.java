package pt.sensae.services.smart.irrigation.backend.infrastructure.boot.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.dialect.PostgresDialect;
import org.springframework.data.relational.core.mapping.NamingStrategy;

import java.util.Optional;

@Configuration
public class CommonDataSourceConfiguration {

    @Bean
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions();
    }

    // Because @EnableJdbcRepositories does not have an option to specify JdbcConverer ref, 
    // we have to use only one SQL dialects.
    @Bean
    public Dialect jdbcDialect() {
        return PostgresDialect.INSTANCE;
    }

    @Bean
    public JdbcMappingContext jdbcMappingContext(Optional<NamingStrategy> namingStrategy,
                                                 JdbcCustomConversions customConversions) {
        JdbcMappingContext mappingContext = new JdbcMappingContext(
                namingStrategy.orElse(NamingStrategy.INSTANCE));
        mappingContext.setSimpleTypeHolder(customConversions.getSimpleTypeHolder());
        return mappingContext;
    }
}
