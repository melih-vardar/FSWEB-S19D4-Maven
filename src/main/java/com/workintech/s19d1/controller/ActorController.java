package com.workintech.s19d1.controller;

import com.workintech.s19d1.dto.ActorRequest;
import com.workintech.s19d1.dto.ActorResponse;
import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.service.ActorService;
import com.workintech.s19d1.util.Converter;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/actor")
public class ActorController {

    private final ActorService actorService;

    @GetMapping
    public List<ActorResponse> findAll() {
        List<Actor> allActors = actorService.findAll();
        return Converter.actorResponseConvert(allActors);
    }

    @GetMapping("/{id}")
    public ActorResponse findById(@PathVariable long id) {
        Actor actor = actorService.findById(id);
        return Converter.actorResponseConvert(actor);
    }

    @PostMapping
    public ActorResponse save(@Validated @RequestBody ActorRequest actorRequest) {
        Actor actor = actorRequest.getActor();
        List<Movie> movies = actorRequest.getMovies();
        for (Movie movie : movies) {
            actor.addMovie(movie);
        }
        Actor savedActor = actorService.save(actor);
        return Converter.actorCreateResponseConvert(savedActor);
    }

    // jpadaki save methodu hem update hem create metodu icin kullanilabiliyor

    @PutMapping("/{id}")
    public ActorResponse update(@RequestBody Actor actor, @PathVariable Long id) {
        Actor foundActor = actorService.findById(id);
        actor.setMovies(foundActor.getMovies());
        actor.setId(id);
        actorService.save(foundActor);
        return Converter.actorResponseConvert(foundActor);
    }

    @DeleteMapping("/{id}")
    public ActorResponse delete(@PathVariable long id) {
        Actor removed = actorService.findById(id);
        actorService.delete(removed);
        return Converter.actorResponseConvert(removed);
    }
}