package com.kedialabs.resources;

import java.util.Objects;

import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.kedialabs.domain.Project;
import com.kedialabs.domain.Vendor;
import com.kedialabs.vendor.VendorDto;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/v1/contractor/{contractorId}/project/{projectId}/vendor")
@Named
public class VendorResource {

    @POST
    @Timed
    public Response createUser(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@Valid VendorDto vendorDto){
        Project project = Project.first("id",projectId,"contractor.id",contractorId,"deleted",Boolean.FALSE,"contractor.deleted",Boolean.FALSE);
        if(Objects.isNull(project)){
            throw new NotFoundException("vendor inventory doesn't exist");
        }
        Vendor vendor = new Vendor();
        vendor.setProject(project);
        vendor.setName(vendorDto.getName());
        vendor.setAddressLine1(vendorDto.getAddressLine1());
        vendor.setAddressLine2(vendorDto.getAddressLine2());
        vendor.setCity(vendorDto.getCity());
        vendor.setPinCode(vendorDto.getPinCode());
        vendor.setPhoneNo(vendorDto.getPhoneNo());
        vendor.persist();
        return Response.ok(vendor).build();
    }
    
    @PUT
    @Path("/{vendorId}")
    @Timed
    public Response updateUser(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@PathParam("vendorId") Long vendorId,@Valid VendorDto vendorDto){
        Vendor vendor = Vendor.first("id",vendorId,"project.id",projectId,"project.contractor.id",contractorId,"deleted",Boolean.FALSE,"project.deleted",Boolean.FALSE,"project.contractor.deleted",Boolean.FALSE);
        if(Objects.isNull(vendor)){
            throw new NotFoundException("vendor inventory doesn't exist");
        }
        vendor.setName(vendorDto.getName());
        vendor.setAddressLine1(vendorDto.getAddressLine1());
        vendor.setAddressLine2(vendorDto.getAddressLine2());
        vendor.setCity(vendorDto.getCity());
        vendor.setPinCode(vendorDto.getPinCode());
        vendor.setPhoneNo(vendorDto.getPhoneNo());
        vendor.persist();
        return Response.ok(vendor).build();
    }
    
    @DELETE
    @Path("/{vendorId}")
    @Timed
    public Response deleteUser(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@PathParam("vendorId") Long vendorId){
        Vendor vendor = Vendor.first("id",vendorId,"project.id",projectId,"project.contractor.id",contractorId,"deleted",Boolean.FALSE,"project.deleted",Boolean.FALSE,"project.contractor.deleted",Boolean.FALSE);
        if(Objects.isNull(vendor)){
            throw new NotFoundException("vendor inventory doesn't exist");
        }
        vendor.setDeleted(Boolean.TRUE);
        vendor.persist();
        return Response.ok().build();
    }
    
}
