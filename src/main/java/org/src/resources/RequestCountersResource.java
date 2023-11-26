package org.src.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.src.entities.RequestCounters;

@Transactional
@Produces(MediaType.APPLICATION_JSON)
@Path("/request_counters")
public class RequestCountersResource {
    @GET
    public RequestCounters getCounters() {
        return RequestCounters.getPessimisticReadLockedRequestCounters();
    }
}
