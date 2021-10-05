package com.jejbuitenhuis.spotitube.authentication;

import com.jejbuitenhuis.spotitube.util.database.DAO;
import com.jejbuitenhuis.spotitube.util.database.Query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSessionDAO extends DAO<UserSession>
{
	private static final String QUERY_ALL
		= "SELECT * " +
			"FROM user_sessions;";
	private static final String QUERY_ALL_MATCHING
		= "SELECT * " +
			"FROM user_sessions " +
			"WHERE token = ?;";
	private static final String QUERY_SAVE_SESSION
		= "INSERT INTO user_sessions (user, token) " +
			"VALUES (?, ?);";

	@Override
	protected UserSession parse(ResultSet row) throws SQLException
	{
		return new UserSession(
			row.getString("user"),
			row.getString("token")
		);
	}

	@Override
	protected String getQueryAll()
	{
		return QUERY_ALL;
	}

	@Override
	protected String getQueryAllMatching()
	{
		return QUERY_ALL_MATCHING;
	}

	public void save(UserSession session) throws SQLException
	{
		var query = Query.create()
			.withQuery(QUERY_SAVE_SESSION)
			.withParameters( new Object[]
				 {
					session.getUser(),
					session.getToken().toString()
				 })
			.build();

		query.execute();
	}
}
