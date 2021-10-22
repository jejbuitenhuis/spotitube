package com.jejbuitenhuis.spotitube.authentication.user;

import com.jejbuitenhuis.spotitube.util.database.JPADAO;

public class UserDAO extends JPADAO<User>
{
	private static final String QUERY_ALL
		= "SELECT u FROM User u";
	private static final String QUERY_ALL_MATCHING
		= "SELECT u FROM User u WHERE u.username = ?1";

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
}
