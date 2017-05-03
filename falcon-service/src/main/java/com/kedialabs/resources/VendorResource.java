package com.kedialabs.resources;

import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.Validate;

import com.codahale.metrics.annotation.Timed;
import com.kedialabs.domain.Project;
import com.kedialabs.domain.Vendor;
import com.kedialabs.vendor.VendorDto;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/v1/contractor/{contractorId}/project/{projectId}")
@Named
public class VendorResource {

    @POST
    @Path("/vendor")
    @Timed
    public Response createUser(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@Valid VendorDto vendorDto){
        Validate.notNull(vendorDto,"user create request can't be null");
        Project project = Project.first("id",projectId,"contractor.id",contractorId,"deleted",Boolean.FALSE,"contractor.deleted",Boolean.FALSE);
        Validate.notNull(project,"Project doesn't exist");
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
    @Path("/vendor/{vendorId}")
    @Timed
    public Response updateUser(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@PathParam("vendorId") Long vendorId,@Valid VendorDto vendorDto){
        Validate.notNull(vendorDto,"user update request can't be null");
        Vendor vendor = Vendor.first("id",vendorId,"project.id",projectId,"project.contractor.id",contractorId,"deleted",Boolean.FALSE,"project.deleted",Boolean.FALSE,"project.contractor.deleted",Boolean.FALSE);
        Validate.notNull(vendor,"Vendor doesn't exist");
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
    @Path("/vendor/{vendorId}")
    @Timed
    public Response deleteUser(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@PathParam("vendorId") Long vendorId){
        Vendor vendor = Vendor.first("id",vendorId,"project.id",projectId,"project.contractor.id",contractorId,"deleted",Boolean.FALSE,"project.deleted",Boolean.FALSE,"project.contractor.deleted",Boolean.FALSE);
        Validate.notNull(vendor,"Vendor doesn't exist");
        vendor.setDeleted(Boolean.TRUE);
        vendor.persist();
        return Response.ok().build();
    }
    
}
