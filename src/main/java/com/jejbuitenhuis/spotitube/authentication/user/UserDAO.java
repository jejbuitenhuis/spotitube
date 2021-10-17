package com.jejbuitenhuis.spotitube.authentication.user;

import com.jejbuitenhuis.spotitube.util.database.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends DAO<User>
{
	private static final String QUERY_ALL
		= "SELECT * " +
			"FROM users;";
	private static final String QUERY_ALL_MATCHING
		= "SELECT * " +
			"FROM users " +
			"WHERE username = ?;";

	@Override
	protected User parse(ResultSet row) throws SQLException
	{
		return new User(
			row.getString("username"),
			row.getString("password")
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
		return null;
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
