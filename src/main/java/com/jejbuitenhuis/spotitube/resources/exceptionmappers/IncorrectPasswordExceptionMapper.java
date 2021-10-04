package com.jejbuitenhuis.spotitube.resources.exceptionmappers;

import com.jejbuitenhuis.spotitube.util.exceptions.authentication.IncorrectPasswordException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IncorrectPasswordExceptionMapper
	implements ExceptionMapper<IncorrectPasswordException>
{
	@Override
	public Response toResponse(IncorrectPasswordException e)
	{
		return Response.status(Status.UNAUTHORIZED)
			.entity( new Object()
			{
				public final boolean error = true;
				public final String type = e.getClass().getName();
				public final String message = "Incorrect password";
			})
			.build();
	}
}