package org.src.entities;

import com.fasterxml.jackson.annotation.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Movie extends PanacheEntityBase {
    @Id
    public Long id;
    public String title;
    public int year;
    @Column(columnDefinition = "TEXT") //because it's long
    public String description;

    //TODO CASCADE...
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "MovieActor", joinColumns = {@JoinColumn(name = "movieId")}, inverseJoinColumns = {@JoinColumn(name = "actorId")})
    @JsonIgnoreProperties("actors")
    public List<Actor> actors = new ArrayList<>();

    //TODO CASCADE...
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "movie", fetch = FetchType.EAGER)
    public List<Picture> pictures = new ArrayList<>();

    public static Movie findByName(String query) {
        return find("title", query).firstResult();
        // TODO PARTIAL NAME SEARCH + MULTIPLE RESULTS + SEARCH DESCRIPTIONS + HANDLE NO RESULT???
    }
}