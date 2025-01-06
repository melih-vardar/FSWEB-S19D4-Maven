package com.workintech.s19d1.service;

import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.exceptions.ApiException;
import com.workintech.s19d1.repository.ActorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    @Override
    public Actor save(Actor actor) {
        return actorRepository.save(actor);
    }

    @Override
    public void delete(Actor actor) {
        actorRepository.delete(actor);
    }

    @Override
    public Actor findById(long id) {
        return actorRepository.findById(id).orElseThrow(() -> new ApiException("actor is not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Actor> findAll() {
        return actorRepository.findAll();
    }
}