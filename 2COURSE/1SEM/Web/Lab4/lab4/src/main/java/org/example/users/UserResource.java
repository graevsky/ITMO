package org.example.users;

import org.example.utils.TokenUtils;
import org.example.utils.Utils;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @EJB
    private UserBean userBean;

    @POST
    @Path("/register")
    public Response register(UserDTO userDTO) {
        User existingUser = userBean.findUserByLogin(userDTO.getLogin());
        if (existingUser != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User already exists").build();
        }

        User user = userBean.createUser(userDTO.getLogin(), userDTO.getPassword());
        return Response.ok(user).build();
    }

    @POST
    @Path("/login")
    public Response login(UserDTO userDTO) {
        User user = userBean.findUserByLogin(userDTO.getLogin());
        if (user != null && user.getPassword().equals(Utils.hashPassword(userDTO.getPassword()))) {
            String token = TokenUtils.generateToken(user.getLogin());
            return Response.ok().entity(token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid login or password").build();
    }
}