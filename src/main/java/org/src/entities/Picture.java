package org.src.entities;

import com.fasterxml.jackson.annotation.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Picture extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Lob
    @Column(columnDefinition = "longblob not null")
    public byte[] picture;
    @ManyToOne
    @JsonIgnoreProperties("movie")
    public Movie movie;

}
