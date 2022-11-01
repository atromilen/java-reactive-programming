package cl.atromilen.reactiveprogramming.controller;

import cl.atromilen.reactiveprogramming.model.Director;
import cl.atromilen.reactiveprogramming.service.DirectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/director")
public class DirectorController {

    private DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Director> save(@RequestBody Director director){
        return directorService.save(director);
    }

    @GetMapping
    public Flux<Director> getAll(){
        return directorService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Mono<Director> getById(@PathVariable Long id){
        return directorService.findById(id);
    }

}
