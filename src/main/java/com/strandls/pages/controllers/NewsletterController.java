package com.strandls.pages.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.strandls.pages.ApiConstants;
import com.strandls.pages.pojo.Newsletter;
import com.strandls.pages.pojo.response.NewsletterWithParentChildRelationship;
import com.strandls.pages.services.NewsletterSerivce;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Newsletter Serivce")
@Path(ApiConstants.V1 + ApiConstants.NEWSLETTER)
public class NewsletterController {

	private static final String ENGLISH_LANGAUAGE_ID = "205";
	@Inject
	private NewsletterSerivce newsletterSerivce;
	
	@GET
	@Path("ping")
	@Produces(MediaType.TEXT_PLAIN)
	public String ping() {
		return "pong";
	}

	@GET
	@Path("{objectId}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find Newsletter by ID", notes = "Returns Newsletter details", response = Newsletter.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Newsletter not found", response = String.class) })
	public Response getNewsletter(@PathParam("objectId") String objectId) {
		try {
			Long id = Long.parseLong(objectId);
			Newsletter newsletter = newsletterSerivce.findById(id);
			return Response.status(Status.OK).entity(newsletter).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Path("group")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find Newsletter by ID", notes = "Returns Newsletter details", response = Newsletter.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Newsletter not found", response = String.class) })
	public Response getNewslettersByGroup(@Context HttpServletRequest request,
			@QueryParam("userGroupId") Long userGroupId,
			@QueryParam("languageId") @DefaultValue(ENGLISH_LANGAUAGE_ID) Long languageId) {
		try {
			List<NewsletterWithParentChildRelationship> newsletter = newsletterSerivce
					.getByUserGroupAndLanguage(userGroupId, languageId);
			return Response.status(Status.OK).entity(newsletter).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Save Newsletter", notes = "Returns Newsletter details", response = Newsletter.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "could not save the newsletter", response = String.class) })
	public Response saveNewsletter(@Context HttpServletRequest request, @ApiParam(name = "Newsletter") Newsletter newsletter) {
		try {
			newsletter = newsletterSerivce.save(newsletter);
			return Response.status(Status.OK).entity(newsletter).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
}
