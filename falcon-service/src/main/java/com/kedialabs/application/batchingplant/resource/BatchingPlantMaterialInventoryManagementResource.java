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
import com.kedialabs.batchingplant.InventoryType;
import com.kedialabs.batchingplant.MaterialInventoryVoucherDto;
import com.kedialabs.batchingplant.domain.MaterialInventoryVoucher;
import com.kedialabs.batchingplant.domain.VehicleInventory;
import com.kedialabs.domain.Project;
import com.kedialabs.domain.Vendor;

@Produces(MediaType.APPLICATION_JSON)
@Path("/v1/batching_plant/material_inventory")
@Named
public class BatchingPlantMaterialInventoryManagementResource {
    
    @POST
    @Timed
    @AuthFilter
    public Response createMaterialInventory(@Valid MaterialInventoryVoucherDto requestDto){
        Project project = UserContext.instance().getContext().getUser().getProject();
        if(!requestDto.getMaterialType().getUnit().equals(requestDto.getUnit().getUnit())){
            throw new BadRequestException(String.format("%s should be in %s units", requestDto.getMaterialType().getName(),requestDto.getMaterialType().getUnit().getType()));
        }
        if(InventoryType.IN.equals(requestDto.getInventoryType())){
            if(Objects.nonNull(requestDto.getRecieverVehicleId())){
                throw new BadRequestException("reciever vehicle is not allowed for incoming inventory");
            }
        }else{
            if(Objects.nonNull(requestDto.getSupplierId())){
                throw new BadRequestException("supplier is not allowed for outgoing inventory");
            }
            if(Objects.nonNull(requestDto.getTransportVehicleId())){
                throw new BadRequestException("transporter is not allowed for outgoing inventory");
            }
        }
        Vendor supplier = null;
        if(Objects.nonNull(requestDto.getSupplierId())){
            supplier = Vendor.first("id",requestDto.getSupplierId(),"project",project);
            if(Objects.isNull(supplier)){
                throw new NotFoundException("supplier not found");
            }
        }
        VehicleInventory transportVehicle = null;
        if(Objects.nonNull(requestDto.getTransportVehicleId())){
            transportVehicle = VehicleInventory.first("id",requestDto.getTransportVehicleId(),"project",project);
            if(Objects.isNull(transportVehicle) || transportVehicle.getVendor().getDeleted()){
                throw new NotFoundException("transport vehicle doesn't exist");
            }
        }
        VehicleInventory recieverVehicle = null;
        if(Objects.nonNull(requestDto.getRecieverVehicleId())){
            recieverVehicle = VehicleInventory.first("id",requestDto.getRecieverVehicleId(),"project",project);
            if(Objects.isNull(recieverVehicle) || recieverVehicle.getVendor().getDeleted()){
                throw new NotFoundException("reciever vehicle doesn't exist");
            }
        }
        MaterialInventoryVoucher voucher = new MaterialInventoryVoucher();
        voucher.setProject(project);
        voucher.setInventoryType(requestDto.getInventoryType());
        voucher.setMaterialType(requestDto.getMaterialType());
        voucher.setQuantity(requestDto.getQuantity());
        voucher.setUnit(requestDto.getUnit());
        voucher.setSupplier(supplier);
        voucher.setTransportVehicle(transportVehicle);
        voucher.setRecieverVehicle(recieverVehicle);
        voucher.setTransactionTime(new Timestamp(requestDto.getTransactionTime()));
        voucher.setRemark(requestDto.getRemark());
        if(Objects.nonNull(requestDto.getChallanNo())){
            voucher.setChallanNo(requestDto.getChallanNo());
        }
        if(Objects.nonNull(requestDto.getGateEntryNo())){
            voucher.setGateEntryNo(requestDto.getGateEntryNo());
        }
        if(Objects.nonNull(requestDto.getQualityCheckPassed())){
            voucher.setQalityCheckPassed(requestDto.getQualityCheckPassed());
        }
        if(Objects.nonNull(requestDto.getRoyaltyNo())){
            voucher.setRoyaltyNo(requestDto.getRoyaltyNo());
        }
        if(Objects.nonNull(requestDto.getSource())){
            voucher.setSource(requestDto.getSource());
        }
        if(Objects.nonNull(requestDto.getSourceMeasurement())){
            if(Objects.nonNull(requestDto.getSourceMeasurementUnit())){
                if(!requestDto.getMaterialType().getUnit().equals(requestDto.getSourceMeasurementUnit().getUnit())){
                    throw new BadRequestException(String.format("%s should be in %s units", requestDto.getMaterialType().getName(),requestDto.getMaterialType().getUnit().getType()));
                }
                voucher.setSourceMeasurement(requestDto.getSourceMeasurement(), requestDto.getSourceMeasurementUnit());
            }else{
                throw new BadRequestException("source measurement unit can't be null");
            }
        }
        voucher.setCreatedBy(String.format("%s|%s", UserContext.instance().getContext().getUser().getId(),UserContext.instance().getContext().getUser().getName()));
        voucher.persist();
        return Response.ok(voucher).build();
    }
    
    @GET
    @Path("/list")
    @Timed
    @AuthFilter
    public Response listMaterialInventoryVouchers(@QueryParam("page_no") Integer pageNo,@QueryParam("per_page") Integer perPage){
        Filter filter = new Filter(new Condition("deleted", Operator.eq, Boolean.FALSE));
        filter.setPageNo(pageNo);
        filter.setPerPage(perPage);
        filter.addSortField("id", Boolean.FALSE);
        return Response.ok(MaterialInventoryVoucher.where(filter)).build();
    }
    
    @PUT
    @Path("/{materialInventoryId}")
    @Timed
    @AuthFilter
    public Response updateMaterialInventoryVoucher(@PathParam("materialInventoryId") Long materialInventoryId,@Valid MaterialInventoryVoucherDto requestDto){
        Project project = UserContext.instance().getContext().getUser().getProject();
        MaterialInventoryVoucher voucher = MaterialInventoryVoucher.first("id",materialInventoryId,"project",project,"deleted",Boolean.FALSE);
        if(Objects.isNull(voucher)){
            throw new NotFoundException("material voucher doesn't exist");
        }
        if(!requestDto.getMaterialType().getUnit().equals(requestDto.getUnit().getUnit())){
            throw new BadRequestException(String.format("%s should be in %s units", requestDto.getMaterialType().getName(),requestDto.getMaterialType().getUnit().getType()));
        }
        if(InventoryType.IN.equals(requestDto.getInventoryType())){
            if(Objects.nonNull(requestDto.getRecieverVehicleId())){
                throw new BadRequestException("reciever vehicle is not allowed for incoming inventory");
            }
        }else{
            if(Objects.nonNull(requestDto.getSupplierId())){
                throw new BadRequestException("supplier is not allowed for outgoing inventory");
            }
            if(Objects.nonNull(requestDto.getTransportVehicleId())){
                throw new BadRequestException("transporter is not allowed for outgoing inventory");
            }
        }
        Vendor supplier = null;
        if(Objects.nonNull(requestDto.getSupplierId())){
            supplier = Vendor.first("id",requestDto.getSupplierId(),"project",project);
            if(Objects.isNull(supplier)){
                throw new NotFoundException("supplier not found");
            }
        }
        VehicleInventory transportVehicle = null;
        if(Objects.nonNull(requestDto.getTransportVehicleId())){
            transportVehicle = VehicleInventory.first("id",requestDto.getTransportVehicleId(),"project",project);
            if(Objects.isNull(transportVehicle)){
                throw new NotFoundException("transport vehicle doesn't exist");
            }
        }
        VehicleInventory recieverVehicle = null;
        if(Objects.nonNull(requestDto.getRecieverVehicleId())){
            recieverVehicle = VehicleInventory.first("id",requestDto.getRecieverVehicleId(),"project",project);
            if(Objects.isNull(recieverVehicle)){
                throw new NotFoundException("reciever vehicle doesn't exist");
            }
        }
        voucher.setInventoryType(requestDto.getInventoryType());
        voucher.setMaterialType(requestDto.getMaterialType());
        voucher.setQuantity(requestDto.getQuantity());
        voucher.setUnit(requestDto.getUnit());
        voucher.setSupplier(supplier);
        voucher.setTransportVehicle(transportVehicle);
        voucher.setRecieverVehicle(recieverVehicle);
        voucher.setTransactionTime(new Timestamp(requestDto.getTransactionTime()));
        voucher.setRemark(requestDto.getRemark());
        if(Objects.nonNull(requestDto.getChallanNo())){
            voucher.setChallanNo(requestDto.getChallanNo());
        }
        if(Objects.nonNull(requestDto.getGateEntryNo())){
            voucher.setGateEntryNo(requestDto.getGateEntryNo());
        }
        if(Objects.nonNull(requestDto.getQualityCheckPassed())){
            voucher.setQalityCheckPassed(requestDto.getQualityCheckPassed());
        }
        if(Objects.nonNull(requestDto.getRoyaltyNo())){
            voucher.setRoyaltyNo(requestDto.getRoyaltyNo());
        }
        if(Objects.nonNull(requestDto.getSource())){
            voucher.setSource(requestDto.getSource());
        }
        if(Objects.nonNull(requestDto.getSourceMeasurement())){
            if(Objects.nonNull(requestDto.getSourceMeasurementUnit())){
                if(!requestDto.getMaterialType().getUnit().equals(requestDto.getSourceMeasurementUnit().getUnit())){
                    throw new BadRequestException(String.format("%s should be in %s units", requestDto.getMaterialType().getName(),requestDto.getMaterialType().getUnit().getType()));
                }
                voucher.setSourceMeasurement(requestDto.getSourceMeasurement(), requestDto.getSourceMeasurementUnit());
            }else{
                throw new BadRequestException("source measurement unit can't be null");
            }
        }
        voucher.setUpdatedBy(String.format("%s|%s", UserContext.instance().getContext().getUser().getId(),UserContext.instance().getContext().getUser().getName()));
        voucher.persist();
        return Response.ok(voucher).build();
    }
    
    @DELETE
    @Path("/{materialInventoryId}")
    @Timed
    @AuthFilter
    public Response deleteMaterialInventoryVoucher(@PathParam("materialInventoryId") Long materialInventoryId){
        Project project = UserContext.instance().getContext().getUser().getProject();
        MaterialInventoryVoucher voucher = MaterialInventoryVoucher.first("id",materialInventoryId,"project",project,"deleted",Boolean.FALSE);
        if(Objects.isNull(voucher)){
            throw new NotFoundException("material voucher doesn't exist");
        }
        voucher.setDeleted(Boolean.TRUE);
        voucher.setUpdatedBy(String.format("%s|%s", UserContext.instance().getContext().getUser().getId(),UserContext.instance().getContext().getUser().getName()));
        voucher.persist();
        return Response.ok(voucher).build();
    }
}
