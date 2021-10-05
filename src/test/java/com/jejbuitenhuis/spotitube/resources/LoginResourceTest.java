package com.jejbuitenhuis.spotitube.resources;

import com.jejbuitenhuis.spotitube.authentication.AuthenticationService;
import com.jejbuitenhuis.spotitube.authentication.UserDTO;
import com.jejbuitenhuis.spotitube.authentication.UserSessionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LoginResourceTest
{
	private LoginResource sut;
	private AuthenticationService mockedService;

	@BeforeEach
	void setup()
	{
		this.sut = new LoginResource();

		this.mockedService = Mockito.mock(AuthenticationService.class);

		this.sut.setAuthService(this.mockedService);
	}

	@Test
	void whenLoginIsCalledAUserSessionDTOShouldBeReturnedWithMatchingUser() throws SQLException
	{
		final String username = "henk";
		final var user = new UserDTO(username, "test");
		final var expected = new UserSessionDTO(username, "fake-token");

		Mockito.when( this.mockedService.authenticate(user) )
			.thenReturn(expected);

		var result = this.sut.login(user);

		Mockito.verify(this.mockedService).authenticate(user);

		assertEquals( expected, result.getEntity() );
	}
}
