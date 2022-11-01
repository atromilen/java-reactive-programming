package cl.atromilen.reactiveprogramming.repository;

import cl.atromilen.reactiveprogramming.model.Director;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DirectorRepository extends ReactiveCrudRepository<Director, Long> {
}
