package com.jejbuitenhuis.spotitube.resources.exceptionmappers;

import com.jejbuitenhuis.spotitube.util.exceptions.authentication.NoUserFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoUserFoundExceptionMapper
	implements ExceptionMapper<NoUserFoundException>
{
	@Override
	public Response toResponse(NoUserFoundException e)
	{
		return Response.status(Status.BAD_REQUEST)
			.entity( new Object()
			{
				public final boolean error = true;
				public final String type = e.getClass().getName();
				public final String message = String.format("Can't find a " +
						"user with username \"%s\".", e.user.user);
			})
			.build();
	}
}
