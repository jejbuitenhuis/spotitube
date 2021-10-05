package com.jejbuitenhuis.spotitube.authentication;

import java.util.UUID;

public class UserSession
{
	private final String user;
	private final UUID token;

	public UserSession(String username)
	{
		this.user = username;
		this.token = UUID.randomUUID();
	}

	public UserSession(String username, String token)
	{
		this.user = username;
		this.token = UUID.fromString(token);
	}

	public String getUser()
	{
		return this.user;
	}

	public UUID getToken()
	{
		return this.token;
	}
}
