package com.terumi.isobe.Cerebro.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.terumi.isobe.Cerebro.api.SignalApi;
import com.terumi.isobe.Cerebro.model.UltrasoundImage;
import com.terumi.isobe.Cerebro.rn.ReconstructionRn;
import com.terumi.isobe.Cerebro.util.DateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "/reconstruction")
@Path("/reconstruction")
public class ReconstructionRest {
	
	@Inject
	private ReconstructionRn reconstructionRn;
	
	@Inject
	private DateUtil dateUtil;
	
	@GET
	@Path("/images")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(nickname = "list-ultrasoundImages", value = "List ultrasound images from user", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	@ApiResponses(@ApiResponse(code = 200, message = "Ultrasound images listed.", response = UltrasoundImage.class))
	public List<UltrasoundImage> listUltrasoundImages(@QueryParam("userId") Long userId) {
		return reconstructionRn.listUtrasoundImages(userId);
	}

	@POST
	@Path("/reconstruct")
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(nickname = "reconstruct-signal", value = "Reconstruct a signal", produces = MediaType.APPLICATION_JSON)
	@ApiResponses({ @ApiResponse(code = 200, message = "Reconstruction done"),
			@ApiResponse(code = 401, message = "Fail to reconstruct") })
	public Response reconstructSignal(SignalApi signal) {
		reconstructionRn.loadsServerFiles(signal);
		return Response.ok().build();

//		return Response.status(400).build();
	}
}
