package org.example.point;

import org.example.utils.TokenUtils;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/points")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PointResource {

    @EJB
    private PointBean pointBean;

    @POST
    public Response checkPoint(PointDTO pointDTO, @HeaderParam("Authorization") String token) {
        if (TokenUtils.isTokenExpired(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token expired").build();
        }
        String username = TokenUtils.getUsernameFromToken(token);
        return pointBean.checkPoint(pointDTO.getX(), pointDTO.getY(), pointDTO.getR(), pointDTO.getTimezoneOffset(), username);
    }

    @GET
    public Response getResults(@HeaderParam("Authorization") String token) {
        if (TokenUtils.isTokenExpired(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Token expired").build();
        }
        String username = TokenUtils.getUsernameFromToken(token);
        return pointBean.getResults(username);
    }
}
