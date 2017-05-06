package com.kedialabs.application.batchingplant.resource;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
import com.google.common.collect.Lists;
import com.kedialabs.application.filters.AuthFilter;
import com.kedialabs.application.filters.UserContext;
import com.kedialabs.batchingplant.VehicleInventoryDto;
import com.kedialabs.batchingplant.VehicleInventoryUpdateDto;
import com.kedialabs.batchingplant.domain.VehicleInventory;
import com.kedialabs.domain.Project;
import com.kedialabs.domain.Vendor;

@Produces(MediaType.APPLICATION_JSON)
@Path("/v1/batching_plant/vehicles")
@Named
public class BatchingPlantVehicleInventoryManagementResource {
    
    @POST
    @Timed
    @AuthFilter
    public Response createVehicleInventory(@Valid VehicleInventoryDto vehicleInventoryDto){
        Project project = UserContext.instance().getContext().getUser().getProject();
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
        vehicleInventory.setCreatedBy(String.format("%s|%s", UserContext.instance().getContext().getUser().getId(),UserContext.instance().getContext().getUser().getName()));
        vehicleInventory.persist();
        return Response.ok(vehicleInventory).build();
    }
    
    @GET
    @Path("/all")
    @Timed
    @AuthFilter
    public Response getVehicleInventory(){
        Project project = UserContext.instance().getContext().getUser().getProject();
        List<VehicleInventory> vehicleInventories = VehicleInventory.where("project",project,"deleted",Boolean.FALSE);
        if(Objects.isNull(vehicleInventories) || vehicleInventories.isEmpty()){
            return Response.ok(Lists.newArrayList()).build();
        }
        return Response.ok(vehicleInventories.stream().filter(vehicle -> !vehicle.getVendor().getDeleted()).collect(Collectors.toList())).build();
    }
    
    @PUT
    @Path("/{vehicleInventoryId}")
    @Timed
    @AuthFilter
    public Response updateVehicleInventory(@PathParam("vehicleInventoryId") Long vehicleInventoryId,@Valid VehicleInventoryUpdateDto vehicleInventoryUpdateDto){
        Project project = UserContext.instance().getContext().getUser().getProject();
        VehicleInventory vehicleInventory = VehicleInventory.first("id",vehicleInventoryId,"project",project,"deleted",Boolean.FALSE);
        if(Objects.isNull(vehicleInventory) || vehicleInventory.getVendor().getDeleted()){
            throw new NotFoundException("vehicle inventory doesn't exist");
        }
        vehicleInventory.setDescription(vehicleInventoryUpdateDto.getDescription());
        vehicleInventory.setUpdatedBy(String.format("%s|%s", UserContext.instance().getContext().getUser().getId(),UserContext.instance().getContext().getUser().getName()));
        vehicleInventory.persist();
        return Response.ok(vehicleInventory).build();
    }
    
    @DELETE
    @Path("/{vehicleInventoryId}")
    @Timed
    @AuthFilter
    public Response deleteVehicleInventory(@PathParam("vehicleInventoryId") Long vehicleInventoryId){
        Project project = UserContext.instance().getContext().getUser().getProject();
        VehicleInventory vehicleInventory = VehicleInventory.first("id",vehicleInventoryId,"project",project,"deleted",Boolean.FALSE);
        if(Objects.isNull(vehicleInventory)){
            throw new NotFoundException("vehicle inventory doesn't exist");
        }
        vehicleInventory.setDeleted(Boolean.TRUE);
        vehicleInventory.setUpdatedBy(String.format("%s|%s", UserContext.instance().getContext().getUser().getId(),UserContext.instance().getContext().getUser().getName()));
        vehicleInventory.persist();
        return Response.ok().build();
    }
    
    
}
