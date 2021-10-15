package com.jejbuitenhuis.spotitube.resources.filters;

import com.jejbuitenhuis.spotitube.authentication.AuthenticationService;
import com.jejbuitenhuis.spotitube.util.Settings;
import com.jejbuitenhuis.spotitube.util.exceptions.ExceptionDTO;
import com.jejbuitenhuis.spotitube.util.exceptions.authentication.IncorrectTokenException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter
{
	private final Logger logger = Logger.getLogger( this.getClass().getName() );

	@Inject
	private AuthenticationService authService;

	@Override
	public void filter(ContainerRequestContext requestContext)
	{
		// TODO: Find an elegant way to match eg "/login/" and "/login" without
		// specifying both
		var noAuthEndpoints = Settings.getInstance()
			.get("authentication.no-auth").split(";");
		var uri = requestContext.getUriInfo().getRequestUri();
		var path = uri.getPath();

		if ( !Arrays.asList(noAuthEndpoints).contains(path) )
		{
			var queryString = uri.getQuery();

			if (queryString == null)
				throw new IncorrectTokenException();

			var queryStrings = queryString.split("&");
			String token;

			try
			{
				token = Arrays.stream(queryStrings)
					.filter(q -> q.startsWith("token="))
					.findFirst()
					.orElseThrow(IncorrectTokenException::new)
					.split("=")[1];
			}
			catch (IndexOutOfBoundsException e)
			{
				throw new IncorrectTokenException();
			}

			if ( token.isEmpty() )
				throw new IncorrectTokenException();

			try
			{
				if ( !this.authService.isAuthenticated(token) )
					throw new IncorrectTokenException();
			} catch (SQLException e)
			{
				this.logger.log(
					Level.SEVERE,
					"Something went wrong while trying to verify a token",
					e
				);

				var response = Response.status(Status.INTERNAL_SERVER_ERROR)
					.type(MediaType.APPLICATION_JSON)
					.entity( new ExceptionDTO(
						this.getClass().getName(),
						e.getMessage()
					))
					.build();

				requestContext.abortWith(response);
			}
		}
	}
}
