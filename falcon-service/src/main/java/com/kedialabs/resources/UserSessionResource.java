package com.kedialabs.resources;

import java.util.Objects;
import java.util.UUID;

import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.kedialabs.domain.User;
import com.kedialabs.domain.UserSession;
import com.kedialabs.session.UserLoginRequestDto;
import com.kedialabs.session.UserSessionDetails;

@Produces(MediaType.APPLICATION_JSON)
@Path("/v1/user")
@Named
public class UserSessionResource {
    
    @POST
    @Path("/login")
    @Timed
    public Response tryLogin(@Valid UserLoginRequestDto requestDto){
        User user = User.first("userName",requestDto.getUserName(),"deleted",Boolean.FALSE);
        if(Objects.isNull(user)){
            throw new NotFoundException("user not found");
        }
        if(!Objects.equals(user.getPassword(), requestDto.getPassword())){
            throw new NotAuthorizedException("userName and password doesn't match");
        }
        UserSession userSession = UserSession.first("user",user,"deleted",Boolean.FALSE);
        if(Objects.isNull(userSession)){
            userSession = new UserSession();
            userSession.setUuid(UUID.randomUUID().toString());
            userSession.setUser(user);
            userSession.persist();
        }
        return Response.ok(UserSessionDetails.builder().sessionId(userSession.getUuid()).contractorId(user.getProject().getContractor().getId())
                .projectId(user.getProject().getId()).userType(user.getUserType()).build()).build();
    }
    
    @PUT
    @Path("/{uuid}/logout")
    @Timed
    public Response tryLogout(@PathParam("uuid") String sessionId){
        UserSession userSession = UserSession.first("uuid",sessionId,"deleted",Boolean.FALSE);
        if(Objects.isNull(userSession)){
            return Response.ok().build();
        }
        userSession.setDeleted(Boolean.TRUE);
        userSession.persist();
        return Response.ok().build();
    }
}
