package com.kedialabs.application.batchingplant.resource;

import java.sql.Timestamp;
import java.util.Objects;

import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
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

import org.activejpa.entity.Condition;
import org.activejpa.entity.Condition.Operator;
import org.activejpa.entity.Filter;

import com.codahale.metrics.annotation.Timed;
import com.kedialabs.application.filters.AuthFilter;
import com.kedialabs.application.filters.UserContext;
import com.kedialabs.batchingplant.ConcreteDispatchVoucherDto;
import com.kedialabs.batchingplant.domain.ConcreteDispatchVoucher;
import com.kedialabs.batchingplant.domain.VehicleInventory;
import com.kedialabs.domain.Project;
import com.kedialabs.measurement.Unit;

@Produces(MediaType.APPLICATION_JSON)
@Path("/v1/batching_plant/concrete_dispatch_inventory")
@Named
public class BatchingPlantConcreteDispatchInventoryManagementResource {
    
    @POST
    @Timed
    @AuthFilter
    public Response createConcreteDispatchVoucher(@Valid ConcreteDispatchVoucherDto requestDto){
        Project project = UserContext.instance().getContext().getUser().getProject();
        VehicleInventory transportVehicle = null;
        if(Objects.nonNull(requestDto.getTransportVehicleId())){
            transportVehicle = VehicleInventory.first("id",requestDto.getTransportVehicleId(),"project",project);
            if(Objects.isNull(transportVehicle) || transportVehicle.getVendor().getDeleted()){
                throw new NotFoundException("transport vehicle doesn't exist");
            }
        }
        if(!requestDto.getUnit().getUnit().equals(Unit.CUBIC_METER)){
            throw new BadRequestException(String.format("concrete should be in %s units", Unit.CUBIC_METER.getType()));
        }
        ConcreteDispatchVoucher voucher = new ConcreteDispatchVoucher();
        voucher.setConcreteType(requestDto.getConcreteType());
        voucher.setQuantity(requestDto.getQuantity());
        voucher.setUnit(requestDto.getUnit());
        voucher.setTransportVehicle(transportVehicle);
        voucher.setDispatchTime(new Timestamp(requestDto.getDispatchTime()));
        voucher.setRemark(requestDto.getRemark());
        voucher.setSlump(requestDto.getSlump());
        voucher.setCreatedBy(String.format("%s|%s", UserContext.instance().getContext().getUser().getId(),UserContext.instance().getContext().getUser().getName()));
        voucher.persist();
        return Response.ok(voucher).build();
    }
    
    @GET
    @Path("/list")
    @Timed
    @AuthFilter
    public Response listConcreteDispatchInventoryVouchers(@QueryParam("page_no") Integer pageNo,@QueryParam("per_page") Integer perPage){
    	Filter filter = new Filter(new Condition("deleted", Operator.eq, Boolean.FALSE));
        filter.setPageNo(pageNo);
        filter.setPerPage(perPage);
        filter.addSortField("id", Boolean.FALSE);
        return Response.ok(ConcreteDispatchVoucher.where(filter)).build();
    }
    
    @PUT
    @Path("/{concreteDispatchInventoryId}")
    @Timed
    @AuthFilter
    public Response updateConcreteDispatchVoucher(@PathParam("concreteDispatchInventoryId") Long concreteDispatchInventoryId,@Valid ConcreteDispatchVoucherDto requestDto){
        Project project = UserContext.instance().getContext().getUser().getProject();
        ConcreteDispatchVoucher voucher = ConcreteDispatchVoucher.first("id",concreteDispatchInventoryId,"project",project);
        if(Objects.nonNull(voucher)){
        	throw new NotFoundException("concrete dispatch voucher doesn't exist");
        }
        VehicleInventory transportVehicle = null;
        if(Objects.nonNull(requestDto.getTransportVehicleId())){
            transportVehicle = VehicleInventory.first("id",requestDto.getTransportVehicleId(),"project",project);
            if(Objects.isNull(transportVehicle) || transportVehicle.getVendor().getDeleted()){
                throw new NotFoundException("transport vehicle doesn't exist");
            }
        }
        if(!requestDto.getUnit().getUnit().equals(Unit.CUBIC_METER)){
            throw new BadRequestException(String.format("concrete should be in %s units", Unit.CUBIC_METER.getType()));
        }
        voucher.setConcreteType(requestDto.getConcreteType());
        voucher.setQuantity(requestDto.getQuantity());
        voucher.setUnit(requestDto.getUnit());
        voucher.setTransportVehicle(transportVehicle);
        voucher.setDispatchTime(new Timestamp(requestDto.getDispatchTime()));
        voucher.setRemark(requestDto.getRemark());
        voucher.setSlump(requestDto.getSlump());
        voucher.setUpdatedBy(String.format("%s|%s", UserContext.instance().getContext().getUser().getId(),UserContext.instance().getContext().getUser().getName()));
        voucher.persist();
        return Response.ok(voucher).build();
    }
    
    @DELETE
    @Path("/{concreteDispatchInventoryId}")
    @Timed
    @AuthFilter
    public Response deleteConcreteDispatchVoucher(@PathParam("concreteDispatchInventoryId") Long concreteDispatchInventoryId){
        Project project = UserContext.instance().getContext().getUser().getProject();
        ConcreteDispatchVoucher voucher = ConcreteDispatchVoucher.first("id",concreteDispatchInventoryId,"project",project);
        if(Objects.nonNull(voucher)){
        	throw new NotFoundException("concrete dispatch voucher doesn't exist");
        }
        voucher.setDeleted(Boolean.FALSE);
        voucher.setUpdatedBy(String.format("%s|%s", UserContext.instance().getContext().getUser().getId(),UserContext.instance().getContext().getUser().getName()));
        voucher.persist();
        return Response.ok(voucher).build();
    }
}
