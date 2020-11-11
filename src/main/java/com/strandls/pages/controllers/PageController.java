package com.strandls.pages.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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

import com.strandls.pages.ApiConstants;
import com.strandls.pages.pojo.Page;
import com.strandls.pages.pojo.request.PageCreate;
import com.strandls.pages.pojo.request.PageTreeUpdate;
import com.strandls.pages.pojo.response.PageShow;
import com.strandls.pages.pojo.response.PageTree;
import com.strandls.pages.services.PageSerivce;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	@ApiOperation(value = "get the Page by ID", notes = "Returns page with content details", response = PageShow.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Page not found", response = String.class) })
	public Response getPage(@PathParam("id") String objectId) {
		try {
			Long id = Long.parseLong(objectId);
			Page newsletter = pageService.findById(id);

			return Response.status(Status.OK).entity(new PageShow(newsletter)).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("tree")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find Newsletter by ID", notes = "Returns Newsletter details", response = PageTree.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Newsletter not found", response = String.class) })
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
	@ApiResponses(value = { @ApiResponse(code = 404, message = "could not save the newsletter", response = String.class) })
	public Response savePage(@Context HttpServletRequest request, @ApiParam(name = "page") PageCreate pageCreate) {
		try {
			Page page = pageService.savePage(request, pageCreate);
			return Response.status(Status.OK).entity(page).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@PUT
	@Path("updateParent")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "update the tree structure of the tree", notes = "return the updated hierarachy", response = PageShow.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Page not found", response = String.class) })
	public Response updateTreeStructure(@Context HttpServletRequest request, @ApiParam(name = "pageTree") List<PageTreeUpdate> pageTreeUpdates) {
		try {
			Boolean sticky = pageService.getCheckForStickyPermission(request);
			List<PageTree> pageTrees = pageService.updateTreeStructure(request, pageTreeUpdates, sticky);
			return Response.status(Status.OK).entity(pageTrees).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	@PUT
	@Path("updateParent")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "update the parent of the page", notes = "return the updated hierarachy", response = PageShow.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Page not found", response = String.class) })
	public Response updateParent(@QueryParam("pageId") Long pageId, @QueryParam("parentId") Long parentId) {
		try {
			Page page = pageService.updateParent(pageId, parentId);
			return Response.status(Status.OK).entity(new PageShow(page)).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
}
