package org.src;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
//this line helps avoid recursion when generation JSON
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Movie extends PanacheEntityBase {
    @Id
    public Long id; //using "Base" version of PanacheEntity because ids are custom
    public String title;
    public int year;
    @Column(columnDefinition = "TEXT") //because it's long
    public String description;

    @ManyToMany(cascade = {CascadeType.ALL}) //TODO CASCADE...
    @JoinTable(name = "MovieActor", joinColumns = {@JoinColumn(name = "movieId")}, inverseJoinColumns = {@JoinColumn(name = "actorId")})
    public List<Actor> actors = new ArrayList<>();

    public static Movie findByName(String query) {
        return find("title", query).firstResult();
        // TODO PARTIAL NAME SEARCH + MULTIPLE RESULTS + SEARCH DESCRIPTIONS + HANDLE NO RESULT???
    }
}