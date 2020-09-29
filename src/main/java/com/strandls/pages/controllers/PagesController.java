/**
 * 
 */
package com.strandls.pages.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.strandls.pages.ApiConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author vilay
 *
 */
@Api("Pages Services")
@Path(ApiConstants.V1 + ApiConstants.PAGES)
public class PagesController {

	@GET
	@Path(ApiConstants.PING)
	@Produces(MediaType.TEXT_PLAIN)

	@ApiOperation(value = "ping pong", notes = "returns pong", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "unable to get pong", response = String.class) })

	public Response getPing() {
		return Response.status(Status.OK).entity("PONG").build();
	}

}
