package org.src;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Actor extends PanacheEntityBase {
    @Id
    public long id; //using "Base" version of PanacheEntity because ids are custom
    public String firstName;
    public String lastName;
    public Date bornDate;
}
