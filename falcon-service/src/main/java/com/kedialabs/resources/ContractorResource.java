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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.kedialabs.contractor.ContractorDto;
import com.kedialabs.domain.Contractor;

@Named
@Path("/v1/contractor")
@Produces(value = MediaType.APPLICATION_JSON)
public class ContractorResource {
    
    @POST
    @Timed
    public Response createContractor(@Valid ContractorDto contractorDto) {
        Contractor contractor = new Contractor();
        contractor.setName(contractorDto.getName());
        contractor.setAddressLine1(contractorDto.getAddressLine1());
        contractor.setAddressLine2(contractorDto.getAddressLine2());
        contractor.setCity(contractorDto.getCity());
        contractor.setPinCode(contractorDto.getPinCode());
        contractor.setPhoneNo(contractorDto.getPhoneNo());
        contractor.persist();
        return Response.ok(contractor).build();
    }
    
    @GET 
    @Path("/{contractorId}")
    @Timed
    public Response getContractor(@PathParam("contractorId") Long contractorId){
        Contractor contractor = Contractor.first("id",contractorId,"deleted",Boolean.FALSE);
        if(Objects.isNull(contractor)){
            throw new NotFoundException("Contractor doesn't exist");
        }
        return Response.ok(contractor).build();
    }
    
    @PUT
    @Path("/{contractorId}")
    @Timed
    public Response updateContractor(@PathParam("contractorId") Long contractorId,@Valid ContractorDto contractorDto) {
        Contractor contractor = Contractor.first("id",contractorId,"deleted",Boolean.FALSE);
        if(Objects.isNull(contractor)){
            throw new NotFoundException("Contractor doesn't exist");
        }
        contractor.setName(contractorDto.getName());
        contractor.setAddressLine1(contractorDto.getAddressLine1());
        contractor.setAddressLine2(contractorDto.getAddressLine2());
        contractor.setCity(contractorDto.getCity());
        contractor.setPinCode(contractorDto.getPinCode());
        contractor.setPinCode(contractorDto.getPinCode());
        contractor.persist();
        return Response.ok(contractor).build();
    }
    
    @DELETE
    @Path("/{contractorId}")
    @Timed
    public Response deleteContractor(@PathParam("contractorId") Long contractorId){
        Contractor contractor = Contractor.first("id",contractorId,"deleted",Boolean.FALSE);
        if(Objects.isNull(contractor)){
            throw new NotFoundException("Contractor doesn't exist");
        }
        contractor.setDeleted(Boolean.TRUE);
        contractor.persist();
        return Response.ok().build();
    }
}
