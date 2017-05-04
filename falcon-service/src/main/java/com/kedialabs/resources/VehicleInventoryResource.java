package com.kedialabs.resources;

import java.util.Objects;

import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
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

import org.apache.commons.lang3.Validate;

import com.codahale.metrics.annotation.Timed;
import com.kedialabs.batchingplant.VehicleInventoryDto;
import com.kedialabs.batchingplant.VehicleInventoryUpdateDto;
import com.kedialabs.batchingplant.domain.VehicleInventory;
import com.kedialabs.domain.Project;
import com.kedialabs.domain.Vendor;

@Produces(MediaType.APPLICATION_JSON)
@Path("/v1/contractor/{contractorId}/project/{projectId}/vehicle_inventory")
@Named
public class VehicleInventoryResource {
    
    @POST
    @Timed
    public Response createVehicleInventory(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@Valid VehicleInventoryDto vehicleInventoryDto){
        Project project = Project.first("id",projectId,"contractor.id",contractorId,"deleted",Boolean.FALSE,"contractor.deleted",Boolean.FALSE);
        if(Objects.isNull(project)){
            throw new NotFoundException("Project doesn't exist");
        }
        Vendor vendor = Vendor.first("id",vehicleInventoryDto.getVendorId(),"project",project,"deleted",Boolean.FALSE);
        if(Objects.isNull(vendor)){
            throw new NotFoundException("Vendor doesn't exist");
        }
        VehicleInventory vehicleInventory = new VehicleInventory();
        vehicleInventory.setProject(project);
        vehicleInventory.setVehicleNo(vehicleInventoryDto.getVehicleNo());
        vehicleInventory.setDescription(vehicleInventoryDto.getDescription());
        vehicleInventory.setVendor(vendor);
        vehicleInventory.persist();
        return Response.ok(vehicleInventory).build();
    }
    
    @GET
    @Path("/{vehicleInventoryId}")
    @Timed
    public Response getVehicleInventory(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@PathParam("vehicleInventoryId") Long vehicleInventoryId){
        VehicleInventory vehicleInventory = VehicleInventory.first("id",vehicleInventoryId,"project.id",projectId,"project.contractor.id",contractorId,"deleted",Boolean.FALSE,"project.deleted",Boolean.FALSE,"project.contractor.deleted",Boolean.FALSE);
        if(Objects.isNull(vehicleInventory)){
            throw new NotFoundException("vehicle inventory doesn't exist");
        }
        return Response.ok(vehicleInventory).build();
    }
    
    @PUT
    @Path("/{vehicleInventoryId}")
    @Timed
    public Response updateVehicleInventory(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@PathParam("vehicleInventoryId") Long vehicleInventoryId,@Valid VehicleInventoryUpdateDto vehicleInventoryUpdateDto){
        VehicleInventory vehicleInventory = VehicleInventory.first("id",vehicleInventoryId,"project.id",projectId,"project.contractor.id",contractorId,"deleted",Boolean.FALSE,"project.deleted",Boolean.FALSE,"project.contractor.deleted",Boolean.FALSE);
        if(Objects.isNull(vehicleInventory)){
            throw new NotFoundException("vehicle inventory doesn't exist");
        }
        vehicleInventory.setDescription(vehicleInventoryUpdateDto.getDescription());
        vehicleInventory.persist();
        return Response.ok(vehicleInventory).build();
    }
    
    @DELETE
    @Path("/{vehicleInventoryId}")
    @Timed
    public Response deleteVehicleInventory(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@PathParam("vehicleInventoryId") Long vehicleInventoryId){
        VehicleInventory vehicleInventory = VehicleInventory.first("id",vehicleInventoryId,"project.id",projectId,"project.contractor.id",contractorId,"deleted",Boolean.FALSE,"project.deleted",Boolean.FALSE,"project.contractor.deleted",Boolean.FALSE);
        if(Objects.isNull(vehicleInventory)){
            throw new NotFoundException("vehicle inventory doesn't exist");
        }
        vehicleInventory.setDeleted(Boolean.TRUE);
        vehicleInventory.persist();
        return Response.ok().build();
    }
    
    
}
