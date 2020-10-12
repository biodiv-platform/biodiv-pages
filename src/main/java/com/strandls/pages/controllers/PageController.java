package com.strandls.pages.controllers;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.strandls.pages.ApiConstants;
import com.strandls.pages.pojo.Newsletter;
import com.strandls.pages.pojo.Page;
import com.strandls.pages.services.NewsletterSerivce;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Page Serivce")
@Path(ApiConstants.V1 + ApiConstants.PAGE)
public class PageController {

	@Inject
	private NewsletterSerivce newsletterSerivce;
	
	@GET
	@Path("ping")
	@Produces(MediaType.TEXT_PLAIN)
	public String ping() {
		return "pong";
	}

	@GET
	@Path("{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "get the Page by ID", notes = "Returns page with content details", response = Page.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Page not found", response = String.class) })
	public Response getPage(@PathParam("id") String objectId) {
		try {
			Long id = Long.parseLong(objectId);
			Newsletter newsletter = newsletterSerivce.findById(id);
			
			return Response.status(Status.OK).entity(new Page(newsletter)).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
}
