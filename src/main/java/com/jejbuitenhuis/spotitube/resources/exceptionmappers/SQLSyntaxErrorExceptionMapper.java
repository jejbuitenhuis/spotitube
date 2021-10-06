package com.jejbuitenhuis.spotitube.resources.exceptionmappers;

import com.jejbuitenhuis.spotitube.util.exceptions.StacktraceExceptionDTO;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Provider
public class SQLSyntaxErrorExceptionMapper
	implements ExceptionMapper<SQLException>
{
	@Override
	public Response toResponse(SQLException e)
	{
		var st = Arrays.stream(
				e.getStackTrace()
			).map(s -> String.format(
				"%s:%d %s()",
				s.getClassName(),
				s.getLineNumber(),
				s.getMethodName()
			)).collect( Collectors.toList() );

		return Response.status(Status.INTERNAL_SERVER_ERROR)
			.entity( new StacktraceExceptionDTO(
				e.getClass().getName(),
				e.getMessage(),
				st
			) )
			.build();
	}
}
