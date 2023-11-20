package org.src;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;


import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Actor extends PanacheEntityBase {
    @Id
    public long id; //using "Base" version of PanacheEntity because ids are custom
    public String firstName;
    public String lastName;
    public Date bornDate;
    @ManyToMany(mappedBy = "actors")
    @JsonBackReference //avoid recursion in JSON
    public List<Movie> movies = new ArrayList<>();
}
