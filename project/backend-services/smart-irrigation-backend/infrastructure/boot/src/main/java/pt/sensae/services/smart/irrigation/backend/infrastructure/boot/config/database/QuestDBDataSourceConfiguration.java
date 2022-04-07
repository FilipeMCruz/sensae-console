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
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jdbc.core.convert.*;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.DialectResolver;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@EnableJdbcRepositories(
        jdbcOperationsRef = "jdbcOperationsQuestDB",
        transactionManagerRef = "transactionManagerQuestDB",
        dataAccessStrategyRef = "dataAccessStrategyQuestDB",
        basePackages = {
                "pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb"
        }
)
@EnableAutoConfiguration(
        exclude = {DataSourceAutoConfiguration.class, JdbcRepositoriesAutoConfiguration.class}
)
@Configuration
@ConfigurationPropertiesScan
public class QuestDBDataSourceConfiguration {

    @Bean
    @Qualifier("questdb")
    public NamedParameterJdbcOperations jdbcOperationsQuestDB(
            @Qualifier("questdb") DataSource dataSourceQuestDB
    ) {
        return new NamedParameterJdbcTemplate(dataSourceQuestDB);
    }

    @Bean
    @Qualifier("questdb")
    @ConfigurationProperties(prefix = "spring.datasource.data")
    public HikariDataSource dataSourceDb1() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @Qualifier("questdb")
    public DataAccessStrategy dataAccessStrategyQuestDB(
            @Qualifier("questdb") NamedParameterJdbcOperations operations,
            JdbcConverter jdbcConverter,
            JdbcMappingContext context
    ) {
        return new DefaultDataAccessStrategy(
                new SqlGeneratorSource(context, jdbcConverter,
                        DialectResolver.getDialect(operations.getJdbcOperations())),
                context, jdbcConverter, operations);
    }

    @Bean
    @Qualifier("questdb")
    public PlatformTransactionManager transactionManagerQuestDB(
            @Qualifier("questdb") final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcConverter jdbcConverter(
            JdbcMappingContext mappingContext,
            @Qualifier("questdb") NamedParameterJdbcOperations operations,
            @Lazy @Qualifier("questdb") RelationResolver relationResolver,
            JdbcCustomConversions conversions
    ) {
        DefaultJdbcTypeFactory jdbcTypeFactory = new DefaultJdbcTypeFactory(
                operations.getJdbcOperations());
        Dialect dialect = DialectResolver.getDialect(operations.getJdbcOperations());
        return new BasicJdbcConverter(mappingContext, relationResolver, conversions, jdbcTypeFactory,
                dialect.getIdentifierProcessing());
    }
}
