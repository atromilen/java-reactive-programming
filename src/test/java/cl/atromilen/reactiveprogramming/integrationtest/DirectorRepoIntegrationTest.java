package cl.atromilen.reactiveprogramming.integrationtest;

import cl.atromilen.reactiveprogramming.config.MyPostgresTestContainer;
import cl.atromilen.reactiveprogramming.config.R2DBCTestsConfig;
import cl.atromilen.reactiveprogramming.model.Director;
import cl.atromilen.reactiveprogramming.repository.DirectorRepository;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@ActiveProfiles("test-container")
@DataR2dbcTest
        (includeFilters = @ComponentScan.Filter(
        classes = R2DBCTestsConfig.class,
        type = FilterType.ASSIGNABLE_TYPE
))
public class DirectorRepoIntegrationTest {

    @ClassRule
    public static PostgreSQLContainer<MyPostgresTestContainer> container = MyPostgresTestContainer.getInstance();

    @Autowired
    DirectorRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void whenInsertRecords_onDatabase_shouldReturnTheSavedRecord() {
        StepVerifier.create(repository.findAll())
                .expectNextCount(0)
                .verifyComplete();

        Director peterJackson = new Director(null, "Peter Jackson");
//        Director cristopherNolan = new Director(null, "Cristopher Nolan");

        StepVerifier.create(repository.save(peterJackson))
                .expectNextCount(1)
//                .assertNext(director -> {
//                    assertThat(director.getName()).isEqualTo("Peter Jackson");
//                    assertThat(director.getDirectorId()).isEqualTo(1);
//                })
                .verifyComplete();

        //TODO Add MVC to do a full integration test
    }

    /**
     * By decorating this method with {@link org.springframework.test.context.DynamicPropertySource @DynamicProperties},
     * the properties used by R2DBC connectionFactory will be load once the PostgreSQL container will be started,
     * saving us of writing R2DBC properties definition in application.yml file.
     * @param registry Injected by the Spring test ApplicationContext
     */
    @DynamicPropertySource
    private static void setDatasourceProperty(DynamicPropertyRegistry registry){
        registry.add("spring.r2dbc.url", () -> String.format("r2dbc:tc:postgresql:///%s?TC_IMAGE_TAG=latest", container.getDatabaseName()));
        registry.add("spring.r2dbc.username", container::getUsername);
        registry.add("spring.r2dbc.password", container::getPassword);
    }

}
