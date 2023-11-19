package org.src;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

@Entity
//this line helps avoid recursion when generation JSON
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Actor extends PanacheEntityBase {
    @Id
    public long id; //using "Base" version of PanacheEntity because ids are custom
    public String firstName;
    public String lastName;
    public Date bornDate;

    @ManyToMany(mappedBy = "actors")
    public List<Movie> movies = new ArrayList<>();
}
