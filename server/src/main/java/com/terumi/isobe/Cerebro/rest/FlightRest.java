package com.terumi.isobe.Cerebro.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.terumi.isobe.Cerebro.api.FlightApi;
import com.terumi.isobe.Cerebro.rn.FlightRn;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "/flight")
@Path("/flight")
public class FlightRest {

	@Inject
	private FlightRn flightRn;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(nickname = "listar-voos", value = "Lista todos os vôos", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ApiResponses(@ApiResponse(code = 201, message = "Vôos listados", response = FlightApi.class))
	public List<FlightApi> listar() {
		return flightRn.listar();
	}
}
