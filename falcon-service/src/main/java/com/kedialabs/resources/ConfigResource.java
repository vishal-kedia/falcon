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
import com.kedialabs.batchingplant.ConcreteMixture;
import com.kedialabs.batchingplant.InventoryType;
import com.kedialabs.batchingplant.RawMaterialType;
import com.kedialabs.measurement.MaterialUnit;

@Named
@Path("/v1/config")
@Produces(value = MediaType.APPLICATION_JSON)
public class ConfigResource {
	
	@GET
	@Timed
	public Response getConfig(){
		List<Map<String,Object>> units = Lists.newArrayList();
		for(MaterialUnit unit : MaterialUnit.values()){
			units.add(ImmutableMap.of("name", unit.name(), "label", unit.getName()));
		}
		List<Map<String,Object>> materialTypes = Lists.newArrayList();
		for(RawMaterialType materialType : RawMaterialType.values()){
			materialTypes.add(ImmutableMap.of("name", materialType.name(), "label", materialType.getName()));
		}
		List<Map<String,Object>> inventoryTypes = Lists.newArrayList();
		for(InventoryType inventoryType : InventoryType.values()){
			inventoryTypes.add(ImmutableMap.of("name", inventoryType.name(), "label", inventoryType.getName()));
		}
		List<Map<String,Object>> concreteTypes = Lists.newArrayList();
		for(ConcreteMixture concreteType : ConcreteMixture.values()){
			concreteTypes.add(ImmutableMap.of("name", concreteType.name(), "label", concreteType.getName()));
		}
		return Response.ok(ImmutableMap.of("units", units,"material_types",materialTypes,"inventory_types",inventoryTypes,"concrete_types",concreteTypes)).build();
	}
}
