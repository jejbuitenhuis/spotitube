package com.jejbuitenhuis.spotitube.authentication.usersession;

import com.jejbuitenhuis.spotitube.util.database.JPADAO;

public class UserSessionDAO extends JPADAO<UserSession>
{
	private static final String QUERY_ALL
		= "SELECT us FROM UserSession us";
	private static final String QUERY_ALL_MATCHING
		= "SELECT us FROM UserSession us WHERE us.token = ?1";

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
