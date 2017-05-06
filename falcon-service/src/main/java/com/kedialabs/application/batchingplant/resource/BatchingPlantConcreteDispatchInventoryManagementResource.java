package com.kedialabs.application.batchingplant.resource;

import java.sql.Timestamp;
import java.util.Objects;

import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
}
