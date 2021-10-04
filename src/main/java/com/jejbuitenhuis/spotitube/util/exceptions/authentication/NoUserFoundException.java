package com.jejbuitenhuis.spotitube.util.exceptions.authentication;

import com.jejbuitenhuis.spotitube.authentication.UserDTO;

public class NoUserFoundException extends RuntimeException
{
	public UserDTO user;

	public NoUserFoundException(UserDTO user)
	{
		super();

		this.user = user;
	}
}
