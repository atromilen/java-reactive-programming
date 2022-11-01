package cl.atromilen.reactiveprogramming.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.util.Objects;

@Configuration
@EnableR2dbcRepositories
public class AppConfig extends AbstractR2dbcConfiguration {

    private final Environment env;

    public AppConfig(Environment env) {
        this.env = env;
    }

    @Override
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(Objects.requireNonNull(env.getProperty("spring.r2dbc.url")))
                        .username(Objects.requireNonNull(env.getProperty("spring.r2dbc.username")))
                        .password(Objects.requireNonNull(env.getProperty("spring.r2dbc.password")))
                        .build()
        );
    }
}
