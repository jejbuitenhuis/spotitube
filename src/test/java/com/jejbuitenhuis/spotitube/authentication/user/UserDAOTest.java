package com.jejbuitenhuis.spotitube.authentication.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDAOTest
{
	UserDAO sut;

	@BeforeEach
	void setup()
	{
		this.sut = new UserDAO();
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
