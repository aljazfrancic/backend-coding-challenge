package org.src.resources;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.src.entities.Actor;
import org.src.entities.RequestCounters;

import java.net.URI;
import java.util.List;

@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/actors")
public class ActorResource {
    private static final long pageSize = 5;

    @GET
    public List<Actor> list() {
        RequestCounters.getPessimicticWriteLockedRequestCounters().actorGet++;
        return Actor.listAll();
    }

    @GET
    @Path("/{id}")
    public Actor get(Long id) {
        RequestCounters.getPessimicticWriteLockedRequestCounters().actorGetId++;
        return Actor.findById(id);
    }

    @GET
    @Path("/page/{page}")
    public List<Actor> listPage(int page) {
        RequestCounters.getPessimicticWriteLockedRequestCounters().actorGetPage++;
        return Actor.streamAll().skip(page * pageSize).limit(pageSize).map(Actor.class::cast).toList();
    }

    @POST
    public Response create(Actor actor) {
        RequestCounters.getPessimicticWriteLockedRequestCounters().actorPost++;
        actor.persist();
        return Response.created(URI.create("/actors/" + actor.id)).build();
    }

    @PUT
    @Path("/{id}")
    public Actor update(Long id, Actor actor) {
        RequestCounters.getPessimicticWriteLockedRequestCounters().actorPut++;
        Actor entity = Actor.findById(id, LockModeType.PESSIMISTIC_WRITE);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.firstName = actor.firstName;
        entity.lastName = actor.lastName;
        entity.bornDate = actor.bornDate;

        return entity;
    }

    @DELETE
    @Path("/{id}")
    public void delete(Long id) {
        RequestCounters.getPessimicticWriteLockedRequestCounters().actorDelete++;
        Actor entity = Actor.findById(id, LockModeType.PESSIMISTIC_WRITE);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
