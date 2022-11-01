package cl.atromilen.reactiveprogramming.controller;

import cl.atromilen.reactiveprogramming.model.Director;
import cl.atromilen.reactiveprogramming.service.DirectorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@WebFluxTest
class DirectorControllerTest {
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    DirectorService service;

    @Test
    void whenServiceCreateADirector_onSave_shouldResponseWithHttpStatus201() {
        var expected = getOneDirector();
        expected.setDirectorId(1);
        when(service.save(any(Director.class))).thenReturn(Mono.just(expected));

        webTestClient.post()
                .uri("/director")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(getOneDirector()), Director.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Director.class);
    }

    @Test
    void whenServiceRespondsWithError_onSave_shouldResponseWithHttpStatus500() {
        when(service.save(any(Director.class)))
                .thenReturn(Mono.error(
                        new DataIntegrityViolationException("duplicate key value violates unique constraint \"director_name_key\"")
                ));

        webTestClient.post()
                .uri("/director")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(getOneDirector()), Director.class)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(Director.class);
    }

    @Test
    void whenDirectorsAreFound_onGetAll_shouldResponseDirectorsWithHttpStatus200() {
        var directorsArray = getArrayOfDirectors();
        when(service.findAll()).thenReturn(Flux.just(directorsArray));

        webTestClient.get().uri("/director")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].directorId").isEqualTo("1")
                .jsonPath("$[0].name").isEqualTo("Director 1")
                .jsonPath("$[1].directorId").isEqualTo("2")
                .jsonPath("$[1].name").isEqualTo("Director 2");

    }

    @Test
    void whenNoDirectorsAreFound_onGetAll_shouldResponseEmptyWithHttpStatus200() {
        when(service.findAll()).thenReturn(Flux.empty());

        webTestClient.get().uri("/director")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Director.class).hasSize(0);
    }

    @Test
    void whenDirectorIsFound_onGetByName_shouldResponseDirectorWithHttpStatus200() {
        var director = getOneDirector();
        director.setDirectorId(1);
        when(service.findByName(anyString())).thenReturn(Mono.just(director));

        webTestClient.get().uri("/director/Director 1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.directorId").isEqualTo("1")
                .jsonPath("$.name").isEqualTo("Director 1");
    }

    @Test
    void whenNoDirectorsIsFound_onGetByName_shouldResponseEmptyWithHttpStatus200() {
        when(service.findByName(anyString())).thenReturn(Mono.empty());

        webTestClient.get().uri("/director/name-to-search")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Director.class).isEqualTo(null);
    }

    private static Director getOneDirector() {
        return new Director("Director 1");
    }

    private static Director[] getArrayOfDirectors() {
        return new Director[]{
                new Director(1, "Director 1"),
                new Director(2, "Director 2")
        };
    }

}
