package com.jejbuitenhuis.spotitube.authentication;

import com.jejbuitenhuis.spotitube.util.database.Query;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;
import java.util.UUID;

public class User
{
	private static final String QUERY_SAVE_SESSION
		= "INSERT INTO user_sessions (user, token)" +
			"VALUES (?, ?);";

	private final String username;
	private final String password;
	private final UUID token;

	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
		this.token = UUID.randomUUID();
	}

	public UserSessionDTO getSession()
	{
		return new UserSessionDTO(
			this.username,
			this.token.toString()
		);
	}

	public boolean hasPassword(String password)
	{
		return this.password != null
			&& DigestUtils.sha256Hex(password).equals(this.password);
	}

	public void saveSession() throws SQLException
	{
		var query = Query.create()
			.withQuery(QUERY_SAVE_SESSION)
			.withParameters( new Object[]
				{
					this.username,
					this.token.toString(),
				})
			.build();

		query.execute();
	}
}
