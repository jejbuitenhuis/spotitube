package com.jejbuitenhuis.spotitube.authentication;

import com.jejbuitenhuis.spotitube.authentication.user.UserDTO;
import com.jejbuitenhuis.spotitube.authentication.usersession.UserSessionDTO;

import java.sql.SQLException;

public interface AuthenticationService
{
	UserSessionDTO authenticate(UserDTO user) throws SQLException;
	boolean isAuthenticated(String token) throws SQLException;
}
