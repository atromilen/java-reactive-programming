package cl.atromilen.reactiveprogramming.service;

import cl.atromilen.reactiveprogramming.model.Director;
import cl.atromilen.reactiveprogramming.repository.DirectorRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DirectorServiceImpl implements DirectorService {

    private DirectorRepository repository;

    public DirectorServiceImpl(DirectorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Director> save(Director director) {
        return repository.save(director);
    }

    @Override
    public Flux<Director> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Director> findByName(String name) {
        return repository.findByName(name);
    }
}
