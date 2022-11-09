package cl.atromilen.reactiveprogramming.service;

import cl.atromilen.reactiveprogramming.model.Director;
import cl.atromilen.reactiveprogramming.repository.DirectorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DirtiesContext
@Import(DirectorServiceImpl.class)
@ExtendWith(SpringExtension.class)
class DirectorServiceImplTest {

    @MockBean
    DirectorRepository repository;

    @Autowired
    @InjectMocks
    DirectorServiceImpl directorService;

    @Test
    void whenDirectorsExists_onFindAll_shouldReturnResults() {
        when(repository.findAll()).thenReturn(Flux.just(
                new Director(1, "Director 1"),
                new Director(1, "Director 2")
        ));

        StepVerifier.create(directorService.findAll())
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void whenThereAreNoneDirectors_onFindAll_shouldReturnEmptyFlux() {
        when(repository.findAll()).thenReturn(Flux.empty());

        StepVerifier.create(directorService.findAll())
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void whenRepositoryFailsWithException_onFindAll_shouldPropagateException() {
        when(repository.findAll()).thenReturn(Flux.error(new RuntimeException("some mock exception in runtime")));

        StepVerifier.create(directorService.findAll())
                .expectErrorMatches(
                        ex -> ex instanceof RuntimeException && "some mock exception in runtime".equals(ex.getMessage())
                )
                .verify();
    }

    @Test
    void WhenDirectorIsCreated_onSave_shouldReturnTheCreatedDirectorWithAnId() {
        Director mockDirector = new Director("mockDirector");
        Director mockSavedDirector = new Director(1, "mockDirector");

        when(repository.save(any(Director.class)))
                .thenReturn(Mono.just(mockSavedDirector));

        StepVerifier.create(directorService.save(mockDirector))
                .assertNext(director -> {
                    Assertions.assertEquals(1, director.getDirectorId());
                    Assertions.assertEquals("mockDirector", director.getName());
                })
                .verifyComplete();
    }

    @Test
    void whenOccursAnyKindOfDataException_onSave_shouldPropagateException() {
        when(repository.save(any(Director.class)))
                .thenReturn(Mono.error(new DataIntegrityViolationException("duplicate key value violates unique constraint \"director_name_key\"")));

        StepVerifier.create(directorService.save(new Director("Existing Director")))
                .expectErrorMatches(ex -> ex instanceof DataAccessException &&
                        "duplicate key value violates unique constraint \"director_name_key\"".equals(ex.getMessage()))
                .verify();
    }

    @Test
    void whenSomeDirectorIsFoundByThatName_onFindByName_shouldReturnDirector() {
        when(repository.findByName(anyString())).thenReturn(Mono.just(
                new Director(1, "Director 1")
        ));

        StepVerifier.create(directorService.findByName("Director 1"))
                .assertNext(director -> "Director 1".equals(director.getName()))
                .verifyComplete();
    }

    @Test
    void whenNoDirectorIsFoundByThatName_onFindByName_shouldReturnEmpty() {
        when(repository.findByName(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(directorService.findByName("mock name"))
                .expectNextCount(0)
                .verifyComplete();
    }
}
