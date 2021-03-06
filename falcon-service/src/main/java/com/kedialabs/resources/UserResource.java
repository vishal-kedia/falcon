package com.kedialabs.resources;

import java.util.Objects;

import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hashids.Hashids;
import org.hibernate.validator.constraints.NotBlank;

import com.codahale.metrics.annotation.Timed;
import com.kedialabs.domain.Project;
import com.kedialabs.domain.User;
import com.kedialabs.user.UserDto;
import com.kedialabs.user.UserUpdateDto;

@Produces(MediaType.APPLICATION_JSON)
@Path("/v1/contractor/{contractorId}/project/{projectId}/user")
@Named
public class UserResource {
    
    private final Hashids hashids = new Hashids("password", 10); 
    
    @POST
    @Timed
    public Response createUser(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@Valid UserDto userDto){
        Project project = Project.first("id",projectId,"contractor.id",contractorId,"deleted",Boolean.FALSE,"contractor.deleted",Boolean.FALSE);
        if(Objects.isNull(project)){
            throw new NotFoundException("Project doesn't exist");
        }
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
    
    @GET
    @Path("/{userId}")
    @Timed
    public Response getUser(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@PathParam("userId") Long userId){
        User user = User.first("id",userId,"project.id",projectId,"project.contractor.id",contractorId,"deleted",Boolean.FALSE,"project.deleted",Boolean.FALSE,"project.contractor.deleted",Boolean.FALSE);
        if(Objects.isNull(user)){
            throw new NotFoundException("User doesn't exist");
        }
        return Response.ok(user).build();
    }
    
    @PUT
    @Path("/{userId}")
    @Timed
    public Response updateUser(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@PathParam("userId") Long userId,@Valid UserUpdateDto userDto){
        User user = User.first("id",userId,"project.id",projectId,"project.contractor.id",contractorId,"deleted",Boolean.FALSE,"project.deleted",Boolean.FALSE,"project.contractor.deleted",Boolean.FALSE);
        if(Objects.isNull(user)){
            throw new NotFoundException("User doesn't exist");
        }
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
    @Path("/{userId}/reset_password")
    @Timed
    public Response setPassword(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@PathParam("userId") Long userId,@NotBlank @QueryParam("password") String password){
        User user = User.first("id",userId,"project.id",projectId,"project.contractor.id",contractorId,"deleted",Boolean.FALSE,"project.deleted",Boolean.FALSE,"project.contractor.deleted",Boolean.FALSE);
        if(Objects.isNull(user)){
            throw new NotFoundException("User doesn't exist");
        }
        user.setPassword(password);
        user.persist();
        return Response.ok(user).build();
    }
    
    @DELETE
    @Path("/{userId}")
    @Timed
    public Response deleteUser(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@PathParam("userId") Long userId){
        User user = User.first("id",userId,"project.id",projectId,"project.contractor.id",contractorId,"deleted",Boolean.FALSE,"project.deleted",Boolean.FALSE,"project.contractor.deleted",Boolean.FALSE);
        if(Objects.isNull(user)){
            throw new NotFoundException("User doesn't exist");
        }
        user.setDeleted(Boolean.TRUE);
        user.persist();
        return Response.ok().build();
    }
}
