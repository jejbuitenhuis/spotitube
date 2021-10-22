package com.jejbuitenhuis.spotitube.authentication.usersession;

import com.jejbuitenhuis.spotitube.authentication.user.User;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "user_sessions")
@Entity
public class UserSession
{
	@Id
	@OneToOne
	@JoinColumn(name = "user", referencedColumnName = "username")
	private User user;

	@Id
	private UUID token;

	protected UserSession() {}

	public UserSession(User username)
	{
		this.user = username;
		this.token = UUID.randomUUID();
	}

	public UserSession(User username, String token)
	{
		this.user = username;
		this.token = UUID.fromString(token);
	}

	public User getUser()
	{
		return this.user;
	}

	public UUID getToken()
	{
		return this.token;
	}
}
