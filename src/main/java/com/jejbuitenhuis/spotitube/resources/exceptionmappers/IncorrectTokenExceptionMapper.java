package com.jejbuitenhuis.spotitube.resources.exceptionmappers;

import com.jejbuitenhuis.spotitube.util.exceptions.ExceptionDTO;
import com.jejbuitenhuis.spotitube.util.exceptions.authentication.IncorrectTokenException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IncorrectTokenExceptionMapper
	implements ExceptionMapper<IncorrectTokenException>
{
	@Override
	public Response toResponse(IncorrectTokenException e)
	{
		return Response.status(Response.Status.UNAUTHORIZED)
			.type(MediaType.APPLICATION_JSON)
			.entity( new ExceptionDTO(
				e.getClass().getName(),
				"Incorrect or no token given"
			) )
			.build();
	}
}
