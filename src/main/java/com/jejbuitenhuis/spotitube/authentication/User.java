package com.jejbuitenhuis.spotitube.authentication;

import org.apache.commons.codec.digest.DigestUtils;

public class User
{
	private final String username;
	private final String password;

	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	public UserSession createSession()
	{
		return new UserSession(this.username);
	}

	public boolean hasPassword(String password)
	{
		return this.password != null
			&& DigestUtils.sha256Hex(password).equals(this.password);
	}
}
