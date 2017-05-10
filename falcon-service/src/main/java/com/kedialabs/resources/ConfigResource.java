package com.kedialabs.resources;

import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.kedialabs.measurement.MaterialUnit;

@Named
@Path("/v1/config")
@Produces(value = MediaType.APPLICATION_JSON)
public class ConfigResource {
	
	@GET
	@Timed
	public Response getConfig(){
		List<Map<String,Object>> config = Lists.newArrayList();
		for(MaterialUnit unit : MaterialUnit.values()){
			config.add(ImmutableMap.of("name", unit.name(), "label", unit.getName()));
		}
		return Response.ok(ImmutableMap.of("units", config)).build();
	}
}
