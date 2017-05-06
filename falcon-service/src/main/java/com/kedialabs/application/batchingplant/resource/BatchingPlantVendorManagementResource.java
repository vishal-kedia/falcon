package com.kedialabs.application.batchingplant.resource;

import java.util.List;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.kedialabs.application.filters.AuthFilter;
import com.kedialabs.application.filters.UserContext;
import com.kedialabs.domain.Project;
import com.kedialabs.domain.Vendor;
import com.kedialabs.vendor.VendorDto;

@Named
@Path("/v1/batching_plant/vendors")
@Produces(value = MediaType.APPLICATION_JSON)
public class BatchingPlantVendorManagementResource {


    @POST
    @Timed
    @AuthFilter
    public Response createUser(@Valid VendorDto vendorDto){
        Vendor vendor = new Vendor();
        vendor.setProject(UserContext.instance().getContext().getUser().getProject());
        vendor.setName(vendorDto.getName());
        vendor.setAddressLine1(vendorDto.getAddressLine1());
        vendor.setAddressLine2(vendorDto.getAddressLine2());
        vendor.setCity(vendorDto.getCity());
        vendor.setPinCode(vendorDto.getPinCode());
        vendor.setPhoneNo(vendorDto.getPhoneNo());
        vendor.setCreatedBy(String.format("%s|%s", UserContext.instance().getContext().getUser().getId(),UserContext.instance().getContext().getUser().getName()));
        vendor.persist();
        return Response.ok(vendor).build();
    }
    
    @GET
    @Path("/all")
    @Timed
    @AuthFilter
    public Response getAllVendors(){
        Project project = UserContext.instance().getContext().getUser().getProject();
        List<Vendor> vendors = Vendor.where("project",project,"deleted",Boolean.FALSE);
        return Response.ok(vendors).build();
    }
    
    @PUT
    @Path("/{vendorId}")
    @Timed
    @AuthFilter
    public Response updateUser(@PathParam("vendorId") Long vendorId,@Valid VendorDto vendorDto){
        Project project = UserContext.instance().getContext().getUser().getProject();
        Vendor vendor = Vendor.first("id",vendorId,"project",project,"deleted",Boolean.FALSE);
        if(Objects.isNull(vendor)){
            throw new NotFoundException("vendor inventory doesn't exist");
        }
        vendor.setName(vendorDto.getName());
        vendor.setAddressLine1(vendorDto.getAddressLine1());
        vendor.setAddressLine2(vendorDto.getAddressLine2());
        vendor.setCity(vendorDto.getCity());
        vendor.setPinCode(vendorDto.getPinCode());
        vendor.setPhoneNo(vendorDto.getPhoneNo());
        vendor.setUpdatedBy(String.format("%s|%s", UserContext.instance().getContext().getUser().getId(),UserContext.instance().getContext().getUser().getName()));
        vendor.persist();
        return Response.ok(vendor).build();
    }
    
    @DELETE
    @Path("/{vendorId}")
    @Timed
    @AuthFilter
    public Response deleteUser(@PathParam("vendorId") Long vendorId){
        Project project = UserContext.instance().getContext().getUser().getProject();
        Vendor vendor = Vendor.first("id",vendorId,"project",project,"deleted",Boolean.FALSE);
        if(Objects.isNull(vendor)){
            throw new NotFoundException("vendor inventory doesn't exist");
        }
        vendor.setDeleted(Boolean.TRUE);
        vendor.setUpdatedBy(String.format("%s|%s", UserContext.instance().getContext().getUser().getId(),UserContext.instance().getContext().getUser().getName()));
        vendor.persist();
        return Response.ok().build();
    }
    

}
