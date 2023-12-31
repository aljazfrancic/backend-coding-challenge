package org.src.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.LockModeType;

@Entity
public class RequestCounters extends PanacheEntityBase {
    @Id
    public Long id;

    public static RequestCounters getPessimisticWriteLockedRequestCounters() {
        return RequestCounters.findById(1, LockModeType.PESSIMISTIC_WRITE);
    }

    public static RequestCounters getPessimisticReadLockedRequestCounters() {
        return RequestCounters.findById(1, LockModeType.PESSIMISTIC_READ);
    }

    //Actor
    public Integer actorGet;
    public Integer actorPost;
    public Integer actorGetPage;
    public Integer actorGetId;
    public Integer actorPut;
    public Integer actorDelete;
    //MovieActor
    public Integer movieActorGet;
    public Integer movieActorPost;
    public Integer movieActorGetId;
    public Integer movieActorPut;
    public Integer movieActorDelete;
    //Movie
    public Integer movieGet;
    public Integer moviePost;
    public Integer movieGetPage;
    public Integer movieSearch;
    public Integer movieGetId;
    public Integer moviePut;
    public Integer movieDelete;
    //Picture
    public Integer pictureGet;
    public Integer picturePost;
    public Integer pictureGetId;
    public Integer pictureGetDirectId;
    public Integer picturePut;
    public Integer pictureDelete;
}
