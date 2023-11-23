package org.src.resources;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
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

import java.net.URI;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/actors")
public class ActorResource {
    private static final long pageSize = 5;

    @GET
    public List<Actor> list() {
        return Actor.listAll();
    }

    @GET
    @Path("/{id}")
    public Actor get(Long id) {
        return Actor.findById(id);
    }

    @GET
    @Transactional
    @Path("/page/{page}")
    public List<PanacheEntityBase> listPage(int page) {
        return Actor.streamAll().skip(page * pageSize).limit(pageSize).toList();
    }

    @POST
    @Transactional
    public Response create(Actor actor) {
        actor.persist();
        return Response.created(URI.create("/actors/" + actor.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Actor update(Long id, Actor actor) {
        Actor entity = Actor.findById(id);
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
    @Transactional
    public void delete(Long id) {
        Actor entity = Actor.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
