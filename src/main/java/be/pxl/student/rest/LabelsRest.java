package be.pxl.student.rest;

import be.pxl.student.entity.Label;
import be.pxl.student.rest.resources.ErrorMessage;
import be.pxl.student.rest.resources.LabelCreateResource;
import be.pxl.student.service.LabelService;
import be.pxl.student.util.exception.DuplicateLabelException;
import be.pxl.student.util.exception.LabelInUseException;
import be.pxl.student.util.exception.LabelNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

@Path("/labels")
public class LabelsRest {


	@Inject
	private LabelService labelService;

	@GET
	@Produces("application/json")
	public Response getLabels() {
		List<Label> labels = labelService.findAllLabels();
		return Response.ok(labels).build();
	}

	@POST
	public Response addLabel(LabelCreateResource labelCreateResource) {
		try {
			labelService.addLabel(labelCreateResource.getName());
			return Response.created(UriBuilder.fromPath("/labels/").build()).build();
		} catch (DuplicateLabelException e) {
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
		}
	}

	@DELETE
	@Path("{id}")
	public Response removeLabel(@PathParam("id") long labelId) {
		try {
			labelService.removeLabel(labelId);
		} catch (LabelNotFoundException | LabelInUseException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(e)).build();
		}
		return Response.status(Response.Status.ACCEPTED).build();
	}
}
