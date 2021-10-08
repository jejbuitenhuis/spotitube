package com.jejbuitenhuis.spotitube.authentication.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest
{
	UserDAO sut;

	@BeforeEach
	void setup()
	{
		this.sut = new UserDAO();
	}

	@Test
	void whenParseIsCalledItShouldReturnANewUserWithTheCorrectValues() throws SQLException
	{
		final var expectedUsername = "test1";
		final var expectedPassword = "test2";
		final var expected = new User(expectedUsername, expectedPassword);

		var mockedResultSet = Mockito.mock(ResultSet.class);

		Mockito.when( mockedResultSet.getString("username") )
			.thenReturn(expectedUsername);
		Mockito.when( mockedResultSet.getString("password") )
			.thenReturn(expectedPassword);

		var result = this.sut.parse(mockedResultSet);

		Mockito.verify(mockedResultSet).getString("username");
		Mockito.verify(mockedResultSet).getString("password");

		assertEquals(expected, result);
	}

	@Test
	void whenGetQueryAllIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "SELECT * FROM users;";

		var result = this.sut.getQueryAll();

		assertEquals(expected, result);
	}

	@Test
	void whenGetQueryAllMatchingIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "SELECT * FROM users WHERE username = ?;";

		var result = this.sut.getQueryAllMatching();

		assertEquals(expected, result);
	}
}
