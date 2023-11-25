package org.src.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;


import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Actor extends PanacheEntityBase {
    @Id
    public long id;
    public String firstName;
    public String lastName;
    public Date bornDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MovieActor", joinColumns = {@JoinColumn(name = "actorId")}, inverseJoinColumns = {@JoinColumn(name = "movieId")})
    @JsonIdentityReference(alwaysAsId = true)
    public List<Movie> movies = new ArrayList<>();
}
