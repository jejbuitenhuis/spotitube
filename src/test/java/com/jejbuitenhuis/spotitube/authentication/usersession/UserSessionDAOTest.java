package com.jejbuitenhuis.spotitube.authentication.usersession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserSessionDAOTest
{
	UserSessionDAO sut;

	@BeforeEach
	void setup()
	{
		this.sut = new UserSessionDAO();
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
}
