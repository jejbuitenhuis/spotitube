package com.jejbuitenhuis.spotitube.authentication;

public class UserDTO
{
	public String user;
	public String password;

	// javax
	public UserDTO() {}

	public UserDTO(String username, String password)
	{
		this.user = username;
		this.password = password;
	}
}
