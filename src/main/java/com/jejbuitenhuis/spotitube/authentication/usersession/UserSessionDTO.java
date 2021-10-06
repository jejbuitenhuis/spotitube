package com.jejbuitenhuis.spotitube.authentication.usersession;

public class UserSessionDTO
{
	public String user;
	public String token;

	public UserSessionDTO(String username, String token)
	{
		this.user = username;
		this.token = token;
	}
}
