package org.src;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
public class Movie extends PanacheEntityBase {
    @Id
    public Long id; //using "Base" version of PanacheEntity because ids are custom
    public String title;
    public int year;
    @Column(columnDefinition = "TEXT") //because it's long
    public String description;

    public static Movie findByName(String query) {
        return find("title", query).firstResult();
        // TODO PARTIAL NAME SEARCH + MULTIPLE RESULTS + SEARCH DESCRIPTIONS + HANDLE NO RESULT???
    }
}