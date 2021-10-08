package com.jejbuitenhuis.spotitube.authentication.user;

import com.jejbuitenhuis.spotitube.authentication.usersession.UserSession;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Objects;

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

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if ( obj == null || this.getClass() != obj.getClass() ) return false;

		User user = (User) obj;

		return Objects.equals(this.username, user.username)
			&& Objects.equals(this.password, user.password);
	}
}
