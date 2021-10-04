package com.jejbuitenhuis.spotitube.authentication;

import java.sql.SQLException;

public interface AuthenticationService
{
	UserSessionDTO authenticate(UserDTO user) throws SQLException;
	boolean isAuthenticated(String token) throws SQLException;
}
