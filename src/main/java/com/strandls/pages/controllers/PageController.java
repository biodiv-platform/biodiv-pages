package com.strandls.pages.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.naming.directory.InvalidAttributesException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.pac4j.core.profile.CommonProfile;

import com.strandls.authentication_utility.filter.ValidateUser;
import com.strandls.authentication_utility.util.AuthUtil;
import com.strandls.pages.ApiConstants;
import com.strandls.pages.pojo.Page;
import com.strandls.pages.pojo.request.PageCreate;
import com.strandls.pages.pojo.request.PageTreeUpdate;
import com.strandls.pages.pojo.request.PageUpdate;
import com.strandls.pages.pojo.response.PageShowFull;
import com.strandls.pages.pojo.response.PageShowMinimal;
import com.strandls.pages.pojo.response.PageTree;
import com.strandls.pages.services.PageSerivce;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.minidev.json.JSONArray;

@Api("Page Serivce")
@Path(ApiConstants.V1 + ApiConstants.PAGE)
public class PageController {

	private static final String ENGLISH_LANGAUAGE_ID = "205";

	@Inject
	private PageSerivce pageService;

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
	@ApiOperation(value = "get the Page by ID", notes = "Returns page with content details", response = PageShowMinimal.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Page not found", response = String.class) })
	public Response getPage(@PathParam("id") String objectId,
			@DefaultValue("minimal") @QueryParam("format") String format) {
		try {
			Long id = Long.parseLong(objectId);
			Page page = pageService.findById(id);

			if ("minimal".equalsIgnoreCase(format))
				return Response.status(Status.OK).entity(new PageShowMinimal(page)).build();
			else if ("full".equalsIgnoreCase(format))
				return Response.status(Status.OK).entity(new PageShowFull(page)).build();
			else
				throw new InvalidAttributesException("Invalid format");
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Path("tree")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find Newsletter by ID", notes = "Returns page details", response = PageTree.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Page not found", response = String.class) })
	@ValidateUser
	public Response getTreeStructure(@Context HttpServletRequest request, @QueryParam("userGroupId") Long userGroupId,
			@QueryParam("languageId") @DefaultValue(ENGLISH_LANGAUAGE_ID) Long languageId) {
		try {
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
	@ApiOperation(value = "Save Page", notes = "Returns Page details", response = PageCreate.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "could not save the page", response = String.class) })
	@ValidateUser
	public Response savePage(@Context HttpServletRequest request, @ApiParam(name = "page") PageCreate pageCreate) {
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
	@ApiOperation(value = "Update Page", notes = "Returns Page details", response = PageCreate.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "could not update the page", response = String.class) })
	@ValidateUser
	public Response updatePage(@Context HttpServletRequest request, @ApiParam(name = "page") PageUpdate pageUpdate) {
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
	@ApiOperation(value = "update the tree structure of the tree", notes = "return the updated hierarachy", response = PageShowMinimal.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Page not found", response = String.class) })
	@ValidateUser
	public Response updateTreeStructure(@Context HttpServletRequest request,
			@ApiParam(name = "pageTree") List<PageTreeUpdate> pageTreeUpdates) {
		try {
			if(pageTreeUpdates.isEmpty())
				return Response.status(Status.OK).entity("Nothing to update").build();
			
			Long pageId = pageTreeUpdates.get(0).getId();
			if (pageService.checkForPagePermission(request, pageId)) {
				Boolean sticky = pageService.getCheckForStickyPermission(request);
				List<PageTree> pageTrees = pageService.updateTreeStructure(request, pageTreeUpdates, sticky);
				return Response.status(Status.OK).entity(pageTrees).build();
			} else {
				return Response.status(Status.UNAUTHORIZED).entity("User is not authorized to update the page tree").build();
			}
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@PUT
	@Path("updateParent")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "update the parent of the page", notes = "return the updated hierarachy", response = PageShowMinimal.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Page not found", response = String.class) })
	@ValidateUser
	public Response updateParent(@Context HttpServletRequest request, @QueryParam("pageId") Long pageId,
			@QueryParam("parentId") Long parentId) {
		try {
			if (pageService.checkForPagePermission(request, pageId)) {
				Page page = pageService.updateParent(pageId, parentId);
				return Response.status(Status.OK).entity(new PageShowMinimal(page)).build();
			} else {
				return Response.status(Status.UNAUTHORIZED).entity("User is not authorized to update the parent").build();
			}
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@POST
	@Path("migrate")
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Migrate the Data from newsletter to pages", notes = "Will be depricated once the migration happens")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Page not found", response = String.class) })
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
	@ApiOperation(value = "Delete the page", notes = "Delete the page")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Page not found", response = Page.class) })
	@ValidateUser
	public Response deletePage(@Context HttpServletRequest request, @PathParam("id") Long pageId) {
		try {
			if (pageService.checkForGroupPermission(request, pageId)) {
				Page page = pageService.deletePage(request, pageId);
				return Response.status(Status.OK).entity(page).build();
			} else {
				return Response.status(Status.UNAUTHORIZED).entity("User is not authorized to delete the page").build();
			}
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
}
