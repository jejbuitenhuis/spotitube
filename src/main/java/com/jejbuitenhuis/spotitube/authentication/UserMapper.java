package com.jejbuitenhuis.spotitube.authentication;

import com.jejbuitenhuis.spotitube.util.database.Query;

import java.sql.SQLException;
import java.util.List;

public class UserMapper
{
	private static final String QUERY_ALL_USERS_WITH_USERNAME
		= "SELECT username, password " +
			"FROM users " +
			"WHERE username = ?;";

	public List<UserDAO> getAllMatching(UserDTO user) throws SQLException
	{
		var query = Query.<UserDAO>create()
			.withQuery(QUERY_ALL_USERS_WITH_USERNAME)
			.withParameters( new Object[]
				{
					user.user,
				})
			.withParser(row -> new UserDAO(
				row.getString("username"),
				row.getString("password")
			))
			.build();
		return query.execute();
	}
}
