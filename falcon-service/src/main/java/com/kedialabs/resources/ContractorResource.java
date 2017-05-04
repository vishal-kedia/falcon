package com.kedialabs.resources;

import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.Validate;

import com.codahale.metrics.annotation.Timed;
import com.kedialabs.contractor.ContractorDto;
import com.kedialabs.domain.Contractor;

@Named
@Path("/v1/contractor")
@Produces(value = MediaType.APPLICATION_JSON)
public class ContractorResource {
    
    @POST
    public Response createContractor(@Valid ContractorDto contractorDto) {
        Validate.notNull(contractorDto,"Request object can't be null");
        Contractor contractor = new Contractor();
        contractor.setName(contractorDto.getName());
        contractor.setAddressLine1(contractorDto.getAddressLine1());
        contractor.setAddressLine2(contractorDto.getAddressLine2());
        contractor.setCity(contractorDto.getCity());
        contractor.setPinCode(contractorDto.getPinCode());
        contractor.setPhoneNo(contractorDto.getPhoneNo());
        contractor.persist();
        return Response.ok().build();
    }
    
    @GET @Path("/{contractorId}")
    public Response getContractor(@PathParam("contractorId") Long contractorId){
        return Response.ok(Contractor.all()).build();
    }
    
    @PUT
    @Path("/{contractorId}")
    public Response updateContractor(@PathParam("contractorId") Long contractorId,@Valid ContractorDto contractorDto) {
        Contractor contractor = Contractor.findById(contractorId);
        Validate.notNull(contractorDto,"Request object can't be null");
        Validate.notNull(contractor, "Contractor doesn't exist");
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
    public Response deleteContractor(@PathParam("contractorId") Long contractorId){
        Contractor contractor = Contractor.findById(contractorId);
        Validate.notNull(contractor, "Contractor doesn't exist");
        contractor.setDeleted(Boolean.TRUE);
        contractor.persist();
        return Response.ok().build();
    }
}
