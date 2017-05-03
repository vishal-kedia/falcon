package com.kedialabs.resources;

import java.util.Objects;

import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.Validate;
import org.hashids.Hashids;

import com.codahale.metrics.annotation.Timed;
import com.kedialabs.domain.Project;
import com.kedialabs.domain.User;
import com.kedialabs.user.UserDto;
import com.kedialabs.user.UserUpdateDto;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/v1/contractor/{contractorId}/project/{projectId}")
@Named
public class UserResource {
    
    private final Hashids hashids = new Hashids("password", 10); 
    
    @POST
    @Path("/user")
    @Timed
    public Response createUser(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@Valid UserDto userDto){
        Validate.notNull(userDto,"user create request can't be null");
        Project project = Project.first("id",projectId,"contractor.id",contractorId,"deleted",Boolean.FALSE,"contractor.deleted",Boolean.FALSE);
        Validate.notNull(project,"Project doesn't exist");
        User user = new User();
        user.setProject(project);
        user.setUserType(userDto.getUserType());
        user.setName(userDto.getName());
        user.setUserName(userDto.getUserName());
        user.setAddressLine1(userDto.getAddressLine1());
        user.setAddressLine2(userDto.getAddressLine2());
        user.setCity(userDto.getCity());
        user.setPinCode(userDto.getPinCode());
        user.setPhoneNo(userDto.getPhoneNo());
        user.setPassword(userDto.getPassword());
        user.persist();
        if(Objects.isNull(user.getPassword())){//set random password if not provided
            user.setPassword(hashids.encode(user.getId()));
            user.persist();
        }
        return Response.ok(user).build();
    }
    
    @PUT
    @Path("/user/{userId}")
    @Timed
    public Response updateUser(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@PathParam("userId") Long userId,@Valid UserUpdateDto userDto){
        Validate.notNull(userDto,"user update request can't be null");
        User user = User.first("id",userId,"project.id",projectId,"project.contractor.id",contractorId,"deleted",Boolean.FALSE,"project.deleted",Boolean.FALSE,"project.contractor.deleted",Boolean.FALSE);
        Validate.notNull(user,"User doesn't exist");
        user.setUserType(userDto.getUserType());
        user.setName(userDto.getName());
        user.setAddressLine1(userDto.getAddressLine1());
        user.setAddressLine2(userDto.getAddressLine2());
        user.setCity(userDto.getCity());
        user.setPinCode(userDto.getPinCode());
        user.setPhoneNo(userDto.getPhoneNo());
        user.persist();
        return Response.ok(user).build();
    }
    
    @PUT
    @Path("/user/{userId}/reset_password")
    @Timed
    public Response setPassword(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@PathParam("userId") Long userId,@QueryParam("password") String password){
        User user = User.first("id",userId,"project.id",projectId,"project.contractor.id",contractorId,"deleted",Boolean.FALSE,"project.deleted",Boolean.FALSE,"project.contractor.deleted",Boolean.FALSE);
        Validate.notNull(user,"User doesn't exist");
        Validate.notNull(password);
        user.setPassword(password);
        user.persist();
        return Response.ok(user).build();
    }
    
    @DELETE
    @Path("/user/{userId}")
    @Timed
    public Response deleteUser(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@PathParam("userId") Long userId){
        User user = User.first("id",userId,"project.id",projectId,"project.contractor.id",contractorId,"deleted",Boolean.FALSE,"project.deleted",Boolean.FALSE,"project.contractor.deleted",Boolean.FALSE);
        Validate.notNull(user,"User doesn't exist");
        user.setDeleted(Boolean.TRUE);
        user.persist();
        return Response.ok().build();
    }
}
