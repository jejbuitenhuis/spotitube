package com.jejbuitenhuis.spotitube.authentication.usersession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserSessionDAOTest
{
	UserSessionDAO sut;

	@BeforeEach
	void setup()
	{
		this.sut = new UserSessionDAO();
	}

	@Test
	void whenParseIsCalledItShouldReturnANewUserSessionWithTheCorrectValues() throws SQLException
	{
		final var expectedUsername = "test1";
		final var expectedToken = "58feed5a-3656-4351-8427-4122c48da2f9";
		final var expected = new UserSession(expectedUsername, expectedToken);

		var mockedResultSet = Mockito.mock(ResultSet.class);

		Mockito.when( mockedResultSet.getString("user") )
			.thenReturn(expectedUsername);
		Mockito.when( mockedResultSet.getString("token") )
			.thenReturn(expectedToken);

		var result = this.sut.parse(mockedResultSet);

		Mockito.verify(mockedResultSet).getString("user");
		Mockito.verify(mockedResultSet).getString("token");

		assertEquals( expected.getUser(), result.getUser() );
		assertEquals( expected.getToken(), result.getToken() );
	}

	@Test
	void whenGetQueryAllIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "SELECT * FROM user_sessions;";

		var result = this.sut.getQueryAll();

		assertEquals(expected, result);
	}

	@Test
	void whenGetQueryAllMatchingIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "SELECT * FROM user_sessions WHERE token = ?;";

		var result = this.sut.getQueryAllMatching();

		assertEquals(expected, result);
	}

	@Test
	void whenGetQuerySaveIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "INSERT INTO user_sessions (user, token) VALUES (?, ?);";

		var result = this.sut.getQuerySave();

		assertEquals(expected, result);
	}

	@Test
	void whenGetQueryDeleteIsCalledItShouldReturnNull()
	{
		var result = this.sut.getQueryDelete();

		assertNull(result);
	}

	@Test
	void whenGetQueryUpdateIsCalledItShouldReturnNull()
	{
		var result = this.sut.getQueryUpdate();

		assertNull(result);
	}
}
