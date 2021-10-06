package com.jejbuitenhuis.spotitube.resources.exceptionmappers;

import com.jejbuitenhuis.spotitube.authentication.user.UserDTO;
import com.jejbuitenhuis.spotitube.util.exceptions.ExceptionDTO;
import com.jejbuitenhuis.spotitube.util.exceptions.authentication.IncorrectPasswordException;
import com.jejbuitenhuis.spotitube.util.exceptions.authentication.NoUserFoundException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import java.sql.SQLSyntaxErrorException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ExceptionMapperTest
{
	@ParameterizedTest
	@MethodSource("getMappers")
	<T extends Throwable> void testMapper(ExceptionMapper<T> mapper, T exception, Status status)
	{
		var response = mapper.toResponse(exception);
		var entity = response.getEntity();

		assertTrue(entity instanceof ExceptionDTO);

		assertEquals( status.getStatusCode(), response.getStatus() );
		assertEquals( exception.getClass().getName(), ( (ExceptionDTO) entity).type );
	}

	static Stream<Arguments> getMappers()
	{
		return Stream.of(
			arguments( new SQLSyntaxErrorExceptionMapper(), new SQLSyntaxErrorException(), Status.INTERNAL_SERVER_ERROR),
			arguments( new NoUserFoundExceptionMapper(), new NoUserFoundException( new UserDTO("test", "test") ), Status.BAD_REQUEST),
			arguments( new IncorrectPasswordExceptionMapper(), new IncorrectPasswordException(), Status.UNAUTHORIZED)
		);
	}
}
