package com.example.zti.resource;
import com.example.zti.model.User;
import com.example.zti.service.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import com.example.zti.model.User;
import com.example.zti.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;



@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    private UserService userService;

    @POST
    @Path("/register")
    public Response register(User user) {
        userService.register(user);
        return Response.ok().build();
    }

    @POST
    @Path("/login")
    public Response login(User user) {
        boolean valid = userService.validate(user.getUsername(), user.getPasswordHash());
        if (valid) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}