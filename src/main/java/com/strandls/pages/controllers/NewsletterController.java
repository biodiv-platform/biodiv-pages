package com.strandls.pages.controllers;

import java.util.List;

import com.strandls.pages.ApiConstants;
import com.strandls.pages.pojo.Newsletter;
import com.strandls.pages.pojo.response.NewsletterWithParentChildRelationship;
import com.strandls.pages.services.NewsletterSerivce;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Controller for newsletter related services.
 *
 * @author Auto-generated
 */
@Tag(name = "Newsletter Service", description = "Endpoints for newsletter operations")
@Path(ApiConstants.V1 + ApiConstants.NEWSLETTER)
public class NewsletterController {

	private static final String ENGLISH_LANGAUAGE_ID = "205";
	@Inject
	private NewsletterSerivce newsletterSerivce;

	@GET
	@Path("ping")
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(summary = "Ping service", description = "Checks if the service is running")
	public String ping() {
		return "pong";
	}

	@GET
	@Path("{objectId}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Find Newsletter by ID", description = "Returns Newsletter details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Newsletter found", content = @Content(schema = @Schema(implementation = Newsletter.class))),
			@ApiResponse(responseCode = "404", description = "Newsletter not found", content = @Content(schema = @Schema(type = "string"))) })
	public Response getNewsletter(@PathParam("objectId") String objectId) {
		try {
			Long id = Long.parseLong(objectId);
			Newsletter newsletter = newsletterSerivce.findById(id);
			return Response.status(Status.OK).entity(newsletter).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("group")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Find Newsletters by User Group", description = "Returns a list of Newsletters with parent-child relationships for a given user group and language")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = NewsletterWithParentChildRelationship.class)))),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(type = "string"))) })
	public Response getNewslettersByGroup(@Context HttpServletRequest request,
			@QueryParam("userGroupId") Long userGroupId,
			@QueryParam("languageId") @DefaultValue(ENGLISH_LANGAUAGE_ID) Long languageId) {
		try {
			List<NewsletterWithParentChildRelationship> newsletter = newsletterSerivce
					.getByUserGroupAndLanguage(userGroupId, languageId);
			return Response.status(Status.OK).entity(newsletter).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Save Newsletter", description = "Saves a newsletter and returns the saved object", requestBody = @RequestBody(description = "Newsletter object that needs to be saved", required = true, content = @Content(schema = @Schema(implementation = Newsletter.class))))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Newsletter saved successfully", content = @Content(schema = @Schema(implementation = Newsletter.class))),
			@ApiResponse(responseCode = "400", description = "Could not save the newsletter", content = @Content(schema = @Schema(type = "string"))) })
	public Response saveNewsletter(@Context HttpServletRequest request, Newsletter newsletter) {
		try {
			newsletter = newsletterSerivce.save(newsletter);
			return Response.status(Status.OK).entity(newsletter).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}
}
