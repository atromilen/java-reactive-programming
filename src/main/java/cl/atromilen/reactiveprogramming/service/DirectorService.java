package cl.atromilen.reactiveprogramming.service;

import cl.atromilen.reactiveprogramming.model.Director;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DirectorService {
    Mono<Director> save(Director director);
    Flux<Director> findAll();
    Mono<Director> findById(Long id);
}
