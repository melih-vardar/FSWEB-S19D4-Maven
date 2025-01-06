package com.workintech.s19d1.controller;

import com.workintech.s19d1.dto.MovieActorRequest;
import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.service.ActorService;
import com.workintech.s19d1.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    private final ActorService actorService;

//    @Autowired
//    public MovieController(MovieService movieService, ActorService actorService) {
//        this.movieService = movieService;
//        this.actorService = actorService;
//    }

    @GetMapping
    public List<Movie> getAllMovies() {
        List<Movie> movieList = movieService.findAll();
        return movieList.stream().map(movie -> {
            movie.setActors(movie.getActors());
            return movie;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable("id") Long id) {
        return movieService.findById(id);
    }

    @PostMapping("/")
    public Movie saveMovieWithActor(@RequestBody MovieActorRequest request) {
        Movie movie = request.getMovie();
        Actor actor = request.getActor();

        movie.addActor(actor);
        actor.addMovie(movie);

        actorService.save(actor);

        Movie savedMovie = movieService.save(movie);

        return savedMovie;
    }
}