package com.jejbuitenhuis.spotitube.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class HelloWorldResource
{
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response hello()
	{
		return Response.ok("{ \"hello\": \"world!\" }").build();
	}
}
