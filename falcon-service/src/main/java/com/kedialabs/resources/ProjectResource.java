package com.kedialabs.resources;

import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
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
import com.kedialabs.domain.Contractor;
import com.kedialabs.domain.Project;
import com.kedialabs.project.ProjectDto;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/v1/contractor/{contractorId}/project")
@Named
public class ProjectResource {
    
    @POST
    @Timed
    public Response createProject(@PathParam("contractorId") Long contractorId,@Valid ProjectDto projectDto){
        Validate.notNull(projectDto,"Project create request can't be null");
        Contractor contractor = Contractor.first("id",contractorId,"deleted",Boolean.FALSE);
        Validate.notNull(contractor, "Contractor doesn't exist");
        Project project = new Project();
        project.setContractor(contractor);
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setLat(projectDto.getLat());
        project.setLng(projectDto.getLng());
        project.setProjectType(projectDto.getProjectType());
        project.persist();
        return Response.ok(project).build();
    }
    
    @GET
    @Path("/{projectId}")
    public Response getProject(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId){
        Project project = Project.first("id",projectId,"contractor.id",contractorId,"contractor.deleted",Boolean.FALSE,"deleted",Boolean.FALSE);
        return Response.ok(project).build();
    }
    
    @PUT
    @Path("/{projectId}")
    @Timed
    public Response updateProject(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId,@Valid ProjectDto projectDto){
        Project project = Project.first("id",projectId,"contractor.id",contractorId,"contractor.deleted",Boolean.FALSE,"deleted",Boolean.FALSE);
        Validate.notNull(project, "Project doesn't exist");
        project.setDescription(projectDto.getDescription());
        project.setLat(projectDto.getLat());
        project.setLng(projectDto.getLng());
        project.setProjectType(projectDto.getProjectType());
        project.persist();
        return Response.ok(project).build();
    }
    
    @DELETE
    @Path("/{projectId}")
    @Timed
    public Response deleteProject(@PathParam("contractorId") Long contractorId,@PathParam("projectId") Long projectId){
        Project project = Project.first("id",projectId,"contractor.id",contractorId,"contractor.deleted",Boolean.FALSE,"deleted",Boolean.FALSE);
        Validate.notNull(project,"Project doesn't exist");
        project.setDeleted(Boolean.TRUE);
        project.persist();
        return Response.ok().build();
    }

}
