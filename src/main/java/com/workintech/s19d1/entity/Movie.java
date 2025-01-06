package com.workintech.s19d1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "movie", schema = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @Column(name = "name")
    @NotNull(message= "film adı null olamaz")
    @NotBlank
    private String name;

    @Column(name = "director_name")
    @NotNull(message= "yönetmen adı null olamaz")
    @NotBlank
    private String directorName;

    @Column(name = "rating")
    @Positive
    private Integer rating;

    @Column(name = "release_date")
    private LocalDate releaseDate;

//    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "movies")
//    private List<Actor> actors = new ArrayList<>();

    //jsonbackreference ile sonsuz donguyu engelleriz.

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER)
    @JoinTable(name = "movie_actor", schema = "movie",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors;

    public void addActor(Actor actor) {
        if(actors == null){
            actors = new ArrayList<>();
        }
        actors.add(actor);
    }
}