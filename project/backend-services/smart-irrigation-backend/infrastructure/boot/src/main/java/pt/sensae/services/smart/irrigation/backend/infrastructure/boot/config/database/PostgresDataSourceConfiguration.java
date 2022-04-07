package pt.sensae.services.smart.irrigation.backend.infrastructure.boot.config.database;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.DataAccessStrategy;
import org.springframework.data.jdbc.core.convert.DefaultDataAccessStrategy;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.convert.SqlGeneratorSource;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.DialectResolver;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@EnableJdbcRepositories(
        jdbcOperationsRef = "jdbcOperationsPostgres",
        transactionManagerRef = "transactionManagerPostgres",
        dataAccessStrategyRef = "dataAccessStrategyPostgres",
        basePackages = {
                "pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.postgres"
        }
)
@EnableAutoConfiguration(
        exclude = {DataSourceAutoConfiguration.class, JdbcRepositoriesAutoConfiguration.class}
)
@Configuration
@ConfigurationPropertiesScan
public class PostgresDataSourceConfiguration {
    @Bean
    @Qualifier("postgres")
    public NamedParameterJdbcOperations jdbcOperationsPostgres(
            @Qualifier("postgres") DataSource dataSourcePostgres
    ) {
        return new NamedParameterJdbcTemplate(dataSourcePostgres);
    }

    @Bean
    @Qualifier("postgres")
    @ConfigurationProperties(prefix = "spring.datasource.business")
    public HikariDataSource dataSourcePostgres() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @Qualifier("postgres")
    public DataAccessStrategy dataAccessStrategyPostgres(
            @Qualifier("postgres") NamedParameterJdbcOperations operations,
            JdbcConverter jdbcConverter,
            JdbcMappingContext context
    ) {
        return new DefaultDataAccessStrategy(
                new SqlGeneratorSource(context, jdbcConverter,
                        DialectResolver.getDialect(operations.getJdbcOperations())),
                context, jdbcConverter, operations);
    }

    @Bean
    @Qualifier("postgres")
    public PlatformTransactionManager transactionManagerPostgres(
            @Qualifier("postgres") final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
