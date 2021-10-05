package com.jejbuitenhuis.spotitube.resources;

import com.jejbuitenhuis.spotitube.authentication.AuthenticationService;
import com.jejbuitenhuis.spotitube.authentication.UserDTO;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.sql.SQLException;

@Path("/login/")
public class LoginResource
{
	private AuthenticationService authService;

	@Inject
	public void setAuthService(AuthenticationService service)
	{
		this.authService = service;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(UserDTO user) throws SQLException
	{
		var session = this.authService.authenticate(user);

		return Response.status(Status.CREATED)
			.entity(session)
			.build();
	}
}
