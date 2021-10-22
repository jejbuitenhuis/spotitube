package com.jejbuitenhuis.spotitube.authentication;

import com.jejbuitenhuis.spotitube.authentication.user.User;
import com.jejbuitenhuis.spotitube.authentication.user.UserDAO;
import com.jejbuitenhuis.spotitube.authentication.user.UserDTO;
import com.jejbuitenhuis.spotitube.authentication.usersession.UserSession;
import com.jejbuitenhuis.spotitube.authentication.usersession.UserSessionDAO;
import com.jejbuitenhuis.spotitube.authentication.usersession.UserSessionDTO;
import com.jejbuitenhuis.spotitube.util.exceptions.authentication.IncorrectPasswordException;
import com.jejbuitenhuis.spotitube.util.exceptions.authentication.NoUserFoundException;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceImplTest
{
	private AuthenticationServiceImpl sut;
	private UserDAO mockedUserDAO;
	private UserSessionDAO mockedUserSessionDAO;

	@BeforeEach
	void setup()
	{
		this.sut = new AuthenticationServiceImpl();

		this.mockedUserDAO = Mockito.mock(UserDAO.class);
		this.mockedUserSessionDAO = Mockito.mock(UserSessionDAO.class);

		this.sut.setUserDAO(this.mockedUserDAO);
		this.sut.setSessionDAO(this.mockedUserSessionDAO);
	}

	@Test
	void whenAuthenticateIsCalledWithAUserItShouldReturnANewUserSessionDTO() throws SQLException
	{
		final String username = "test1";
		final String password = "testPass";
		final String token = "58feed5a-3656-4351-8427-4122c48da2f9";
		final var userDTO = new UserDTO(username, password);
		final var user = Mockito.spy( new User(
			username,
			DigestUtils.sha256Hex(password)
		) );
		final var session = new UserSession(user, token);
		final var usersList = new ArrayList<User>();

		usersList.add(user);

		Mockito.when( this.mockedUserDAO.getAllMatching(userDTO.user) )
			.thenReturn(usersList);
		Mockito.when( user.createSession() )
			.thenReturn(session);

		UserSessionDTO result = this.sut.authenticate(userDTO);

		Mockito.verify(this.mockedUserDAO).getAllMatching(userDTO.user);
		Mockito.verify(user).createSession();
		Mockito.verify(this.mockedUserSessionDAO).save( Mockito.any(UserSession.class) );

		assertEquals( username, result.user );
		assertEquals( token, result.token );
	}

	@Test
	void whenAuthenticateIsCalledWithANonExistentUserANoUserFoundExceptionIsThrown() throws SQLException
	{
		final String username = "non-existent";
		final String password = "testPass";
		final var userDTO = new UserDTO(username, password);

		Mockito.when( this.mockedUserDAO.getAllMatching(userDTO.user) )
			.thenReturn( new ArrayList<>() );

		var result = assertThrows(
			NoUserFoundException.class,
			() -> this.sut.authenticate(userDTO)
		);
		Mockito.verify(this.mockedUserDAO).getAllMatching(userDTO.user);
		assertEquals(userDTO, result.user);
	}

	@Test
	void whenAuthenticateIsCalledWIthAWrongPasswordAnIncorrectPasswordExceptionIsThrows() throws SQLException
	{
		final String username = "test1";
		final String password = "wrong";
		final var userDTO = new UserDTO(username, password);
		final var user = Mockito.spy( new User(
			username,
			DigestUtils.sha256Hex(password)
		) );
		final var usersList = new ArrayList<User>();

		usersList.add(user);

		Mockito.when( this.mockedUserDAO.getAllMatching(userDTO.user) )
			.thenReturn(usersList);
		Mockito.when( user.hasPassword(password) )
			.thenReturn(false);

		assertThrows(IncorrectPasswordException.class,
			() -> this.sut.authenticate(userDTO) );
		Mockito.verify(this.mockedUserDAO).getAllMatching(userDTO.user);
		Mockito.verify(user).hasPassword(password);
	}

	@Test
	void whenIsAuthenticatedIsCalledWithAnAuthenticatedUserItShouldReturnTrue() throws SQLException
	{
		final String token = "58feed5a-3656-4351-8427-4122c48da2f9";
		final var getAllMatchingResult = new ArrayList<UserSession>();
		final var session = new UserSession( new User("user", "pass"), token );

		getAllMatchingResult.add(session);

		Mockito.when( this.mockedUserSessionDAO.getAllMatching( Mockito.any() ) )
				.thenReturn(getAllMatchingResult);

		boolean result = this.sut.isAuthenticated(token);

		Mockito.verify(this.mockedUserSessionDAO).getAllMatching( Mockito.any() );

		assertTrue(result);
	}
}
