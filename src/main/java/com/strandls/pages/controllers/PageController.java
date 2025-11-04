package com.strandls.pages.controllers;

import java.util.List;

import org.pac4j.core.profile.CommonProfile;

import com.strandls.activity.pojo.Activity;
import com.strandls.activity.pojo.CommentLoggingData;
import com.strandls.authentication_utility.filter.ValidateUser;
import com.strandls.authentication_utility.util.AuthUtil;
import com.strandls.pages.ApiConstants;
import com.strandls.pages.pojo.Page;
import com.strandls.pages.pojo.request.PageCreate;
import com.strandls.pages.pojo.request.PageTreeUpdate;
import com.strandls.pages.pojo.request.PageUpdate;
import com.strandls.pages.pojo.request.ReorderingGalleryPage;
import com.strandls.pages.pojo.response.PageShowFull;
import com.strandls.pages.pojo.response.PageShowMinimal;
import com.strandls.pages.pojo.response.PageTree;
import com.strandls.pages.services.PageSerivce;
import com.strandls.user.controller.UserServiceApi;
import com.strandls.user.pojo.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import net.minidev.json.JSONArray;

@Tag(name = "Page Service") // Updated @Api to @Tag
@Path(ApiConstants.V1 + ApiConstants.PAGE)
public class PageController {

	private static final String ENGLISH_LANGAUAGE_ID = "205";

	@Inject
	private PageSerivce pageService;

	@Inject
	private UserServiceApi userServiceApi;

	@GET
	@Path("ping")
	@Produces(MediaType.TEXT_PLAIN)
	// Added OpenAPI 3 annotations for ping endpoint
	@Operation(summary = "Ping endpoint", description = "Checks if the service is running")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = String.class))) })
	public String ping() {
		return "pong";
	}

	@GET
	@Path("{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "get the Page by ID", description = "Returns page with content details") // Updated
																									// @ApiOperation
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = PageShowMinimal.class))), // Added
																																						// 200
																																						// response
			@ApiResponse(responseCode = "404", description = "Page not found", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "400", description = "Invalid format or ID", content = @Content(schema = @Schema(implementation = String.class))) }) // Added
																																								// 400
																																								// response
	public Response getPage(@PathParam("id") String objectId,
			@DefaultValue("minimal") @QueryParam("format") String format) {
		try {
			Long id = Long.parseLong(objectId);
			Page page = pageService.findPageById(id);

			if ("minimal".equalsIgnoreCase(format))
				return Response.status(Status.OK).entity(new PageShowMinimal(page)).build();
			else if ("full".equalsIgnoreCase(format)) {
				User user = userServiceApi.getUser(page.getAutherId().toString());
				return Response.status(Status.OK).entity(new PageShowFull(page, user)).build();
			} else
				throw new IllegalArgumentException("Invalid format");
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Path("tree")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Find Newsletter by ID", description = "Returns page details") // Updated @ApiOperation
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PageTree.class)))), // Updated
																																										// responseContainer
			@ApiResponse(responseCode = "404", description = "Page not found", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = String.class))) }) // Added
																																					// 400
																																					// response
	// @ValidateUser // Keep as is, not an OpenAPI annotation
	public Response getTreeStructure(@Context HttpServletRequest request, @QueryParam("userGroupId") String userGroupIdStr,
			@QueryParam("languageId") @DefaultValue(ENGLISH_LANGAUAGE_ID) Long languageId) {
		try {
			Long userGroupId = (userGroupIdStr == null || userGroupIdStr.isEmpty()) ? null : Long.parseLong(userGroupIdStr);
			Boolean sticky = pageService.getCheckForStickyPermission(request);
			List<PageTree> page = pageService.getTreeStructure(userGroupId, languageId, sticky);
			return Response.status(Status.OK).entity(page).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Save Page", description = "Returns Page details") // Updated @ApiOperation
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Page.class))), // Changed
																																			// response
																																			// to
																																			// Page.class
			@ApiResponse(responseCode = "404", description = "Could not save the page", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = String.class))), // Added
																																					// 401
																																					// response
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = String.class))) }) // Added
																																					// 400
																																					// response
	@ValidateUser
	public Response savePage(@Context HttpServletRequest request,
			@Parameter(description = "page", required = true) PageCreate pageCreate) { // Updated @ApiParam
		try {
			Long userGroupId = pageCreate.getUserGroupId();
			if (pageService.checkForGroupPermission(request, userGroupId)) {
				Page page = pageService.savePage(request, pageCreate);
				return Response.status(Status.OK).entity(page).build();
			} else {
				return Response.status(Status.UNAUTHORIZED).entity("Not authorized to add page to the group").build();
			}
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Update Page", description = "Returns Page details") // Updated @ApiOperation
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Page.class))), // Changed
																																			// response
																																			// to
																																			// Page.class
			@ApiResponse(responseCode = "404", description = "Could not update the page", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = String.class))), // Added
																																					// 401
																																					// response
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = String.class))) }) // Added
																																					// 400
																																					// response
	@ValidateUser
	public Response updatePage(@Context HttpServletRequest request,
			@Parameter(description = "page", required = true) PageUpdate pageUpdate) { // Updated @ApiParam
		try {
			Long pageId = Long.parseLong(pageUpdate.getId());
			if (pageService.checkForPagePermission(request, pageId)) {
				Page page = pageService.updatePage(request, pageUpdate);
				return Response.status(Status.OK).entity(page).build();
			} else {
				return Response.status(Status.UNAUTHORIZED).entity("Not authorized to update the page").build();
			}
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@PUT
	@Path("updateTree")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "update the tree structure of the tree", description = "return the updated hierarachy") // Updated
																													// @ApiOperation
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PageTree.class)))), // Updated
																																										// response
																																										// to
																																										// List<PageTree>
			@ApiResponse(responseCode = "404", description = "Page not found", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = String.class))), // Added
																																					// 401
																																					// response
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = String.class))) }) // Added
																																					// 400
																																					// response
	@ValidateUser
	public Response updateTreeStructure(@Context HttpServletRequest request,
			@Parameter(description = "pageTree", required = true) List<PageTreeUpdate> pageTreeUpdates) { // Updated
																											// @ApiParam
		try {
			if (pageTreeUpdates.isEmpty())
				return Response.status(Status.OK).entity("Nothing to update").build();

			Long pageId = pageTreeUpdates.get(0).getId();
			if (pageService.checkForPagePermission(request, pageId)) {
				Boolean sticky = pageService.getCheckForStickyPermission(request);
				List<PageTree> pageTrees = pageService.updateTreeStructure(request, pageTreeUpdates, sticky);
				return Response.status(Status.OK).entity(pageTrees).build();
			} else {
				return Response.status(Status.UNAUTHORIZED).entity("User is not authorized to update the page tree")
						.build();
			}
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@PUT
	@Path("updateParent")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "update the parent of the page", description = "return the updated hierarachy") // Updated
																											// @ApiOperation
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = PageShowMinimal.class))),
			@ApiResponse(responseCode = "404", description = "Page not found", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = String.class))), // Added
																																					// 401
																																					// response
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = String.class))) }) // Added
																																					// 400
																																					// response
	@ValidateUser
	public Response updateParent(@Context HttpServletRequest request, @QueryParam("pageId") Long pageId,
			@QueryParam("parentId") Long parentId) {
		try {
			if (pageService.checkForPagePermission(request, pageId)) {
				Page page = pageService.updateParent(pageId, parentId);
				return Response.status(Status.OK).entity(new PageShowMinimal(page)).build();
			} else {
				return Response.status(Status.UNAUTHORIZED).entity("User is not authorized to update the parent")
						.build();
			}
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@POST
	@Path("migrate")
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(summary = "Migrate the Data from newsletter to pages", description = "Will be depricated once the migration happens") // Updated
																																		// @ApiOperation
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Void.class))), // Changed
																																			// response
																																			// to
																																			// Void.class
																																			// for
																																			// no
																																			// content
			@ApiResponse(responseCode = "404", description = "Page not found", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = String.class))) }) // Added
																																						// 401
																																						// response
	@ValidateUser
	public Response migrateData(@Context HttpServletRequest request) {

		CommonProfile profile = AuthUtil.getProfileFromRequest(request);
		if (profile == null)
			return Response.status(Status.UNAUTHORIZED).entity("Missing authorization header").build();

		JSONArray roles = (JSONArray) profile.getAttribute("roles");

		if (!roles.contains("ROLE_ADMIN"))
			return Response.status(Status.UNAUTHORIZED).build();

		pageService.migrate();

		return Response.status(Status.OK).build();
	}

	@DELETE
	@Path("{id}")
	@Operation(summary = "Delete the page", description = "Delete the page") // Updated @ApiOperation
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Page.class))),
			@ApiResponse(responseCode = "404", description = "Page not found", content = @Content(schema = @Schema(implementation = String.class))), // Changed
																																						// response
																																						// to
																																						// String.class
																																						// as
																																						// per
																																						// reference
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = String.class))), // Added
																																					// 401
																																					// response
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = String.class))) }) // Added
																																					// 400
																																					// response
	@ValidateUser
	public Response deletePage(@Context HttpServletRequest request, @PathParam("id") Long pageId) {
		try {
			if (pageService.checkForPagePermission(request, pageId)) {
				Page page = pageService.deletePage(request, pageId);
				return Response.status(Status.OK).entity(page).build();
			} else {
				return Response.status(Status.UNAUTHORIZED).entity("User is not authorized to delete the page").build();
			}
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@PUT
	@Path(ApiConstants.GALLERY + ApiConstants.REORDERING + "/{pageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ValidateUser
	@Operation(summary = "Reorder Page gallery display order", description = "return page data") // Updated
																									// @ApiOperation
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Page.class))),
			@ApiResponse(responseCode = "400", description = "Unable to retrieve the data", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = String.class))) }) // Added
																																						// 401
																																						// response
	public Response reorderingHomePageGallerySlider(@Context HttpServletRequest request,
			@PathParam("pageId") String pageId,
			@Parameter(description = "reorderingHomePage", required = true) List<ReorderingGalleryPage> reorderingGalleryPage) { // Updated
																																	// @ApiParam
		try {
			Long pgId = Long.parseLong(pageId);

			if (pageService.checkForPagePermission(request, pgId)) {
				Page result = pageService.reorderingPageGallerySlider(pgId, reorderingGalleryPage);

				return Response.status(Status.OK).entity(result).build();
			} else {
				return Response.status(Status.UNAUTHORIZED).entity("User is not authorized to delete the page").build();
			}

		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@PUT
	@Path(ApiConstants.GALLERY + ApiConstants.REMOVE + "/{pageId}/{galleryId}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ValidateUser
	@Operation(summary = "Delete Page gallery data by gallery Id", description = "return page data") // Updated
																										// @ApiOperation
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Page.class))),
			@ApiResponse(responseCode = "400", description = "Unable to retrieve the data", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = String.class))) }) // Added
																																						// 401
																																						// response
	public Response removeGalleryData(@Context HttpServletRequest request, @PathParam("pageId") String pageId,
			@PathParam("galleryId") String galleryId) {
		try {
			Long pgId = Long.parseLong(pageId);
			Long pageGalleryId = Long.parseLong(galleryId);

			if (pageService.checkForPagePermission(request, pgId)) {
				Page page = pageService.removePageGallerySlider(pageGalleryId, pgId);

				return Response.status(Status.OK).entity(page).build();
			} else {
				return Response.status(Status.UNAUTHORIZED).entity("User is not authorized to delete the page").build();
			}

		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path(ApiConstants.ADD + ApiConstants.COMMENT)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ValidateUser
	@Operation(summary = "Adds a comment", description = "Return the current activity") // Updated @ApiOperation
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Activity.class))),
			@ApiResponse(responseCode = "400", description = "Unable to log a comment", content = @Content(schema = @Schema(implementation = String.class))) })
	public Response addCommnet(@Context HttpServletRequest request,
			@Parameter(description = "commentData") CommentLoggingData commentDatas) { // Updated @ApiParam
		try {
			Activity result = pageService.addPageComment(request, commentDatas);
			return Response.status(Status.OK).entity(result).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}

	@POST
	@Path(ApiConstants.DELETE + ApiConstants.COMMENT + "/{commentId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ValidateUser
	@Operation(summary = "Deletes a comment", description = "Return the current activity") // Updated @ApiOperation
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = Activity.class))),
			@ApiResponse(responseCode = "400", description = "Unable to log a comment", content = @Content(schema = @Schema(implementation = String.class))) })
	public Response deleteCommnet(@Context HttpServletRequest request,
			@Parameter(description = "commentData") CommentLoggingData commentDatas,
			@PathParam("commentId") String commentId) { // Updated @ApiParam
		try {
			Activity result = pageService.removePagesComment(request, commentDatas, commentId);
			return Response.status(Status.OK).entity(result).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}

}
