package cl.atromilen.reactiveprogramming.repository;

import cl.atromilen.reactiveprogramming.model.Director;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface DirectorRepository extends ReactiveCrudRepository<Director, Long> {
    Mono<Director> findByName(String name);
}
