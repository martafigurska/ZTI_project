package com.example.zti.resource;

import com.example.zti.model.User;
import com.example.zti.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    private UserService userService;

    @POST
    @Path("/register")
    public Response register(User user) {
        if (user.getUsername() == null || user.getPasswordHash() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Brak danych").build();
        }
        try {
            userService.register(user);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).entity("Użytkownik istnieje").build();
        }
    }

    @POST
    @Path("/login")
    public Response login(User user) {
        if (user.getUsername() == null || user.getPasswordHash() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Brak danych").build();
        }

        boolean valid = userService.validate(user.getUsername(), user.getPasswordHash());
        if (valid) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Nieprawidłowy login/hasło").build();
        }
    }
}
