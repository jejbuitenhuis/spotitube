package com.jejbuitenhuis.spotitube.authentication.usersession;

import com.jejbuitenhuis.spotitube.util.database.DAO;

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
	private static final String QUERY_SAVE
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

	@Override
	protected String getQuerySave()
	{
		return QUERY_SAVE;
	}

	@Override
	protected String getQueryDelete()
	{
		return null;
	}

	@Override
	protected String getQueryUpdate()
	{
		return null;
	}
}
