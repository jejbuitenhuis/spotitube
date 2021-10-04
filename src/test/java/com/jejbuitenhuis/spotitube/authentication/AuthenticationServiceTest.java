package com.jejbuitenhuis.spotitube.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest
{
	private AuthenticationService sut;
	private

	@BeforeEach
	void setup()
	{
		this.sut = new ImplementedAuthenticationService();
	}

	@Test
	void authenticate()
	{
	}

	@Test
	void isAuthenticated()
	{
	}
}