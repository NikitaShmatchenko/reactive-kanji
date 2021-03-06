package org.reactive.kanji;

import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.connectionfactory.init.CompositeDatabasePopulator;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Slf4j
@Configuration
@EnableR2dbcRepositories("org.reactive.rocket.persistence.repository")
public class Config extends AbstractR2dbcConfiguration {

    @Value("${dictionary.api.uri}")
    private String dictionaryServiceUri;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        return H2ConnectionFactory.inMemory("testdb");
    }

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("db-schema.sql")));
        initializer.setDatabasePopulator(populator);

        return initializer;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .uriBuilderFactory(new DefaultUriBuilderFactory(dictionaryServiceUri))
                .filter((request, next) -> {
                    log.info("API Request: {} {}", request.method(), request.url());
                    request.headers().forEach(
                            (name, values) -> log.info("{}={}", name, String.join("|", values)));
                    return next.exchange(request);
                })
                .build();
    }
}
