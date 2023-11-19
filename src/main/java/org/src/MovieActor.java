package org.src;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
public class MovieActor extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id; //this id is not really necessary, but could be useful
    public Long movieId;
    public Long actorId;
}