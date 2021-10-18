package com.jejbuitenhuis.spotitube.resources.exceptionmappers;


import com.jejbuitenhuis.spotitube.util.exceptions.ExceptionDTO;
import com.jejbuitenhuis.spotitube.util.exceptions.track.TrackNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TrackNotFoundExceptionMapper
		implements ExceptionMapper<TrackNotFoundException>
{
	@Override
	public Response toResponse(TrackNotFoundException e)
	{
		return Response.status(Response.Status.BAD_REQUEST)
			.type(MediaType.APPLICATION_JSON)
			.entity( new ExceptionDTO(
				e.getClass().getName(),
				"Incorrect or no token given"
			) )
			.build();
	}
}
