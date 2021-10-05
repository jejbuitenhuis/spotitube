package com.jejbuitenhuis.spotitube.authentication;

import com.jejbuitenhuis.spotitube.util.exceptions.authentication.IncorrectPasswordException;
import com.jejbuitenhuis.spotitube.util.exceptions.authentication.NoUserFoundException;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

public class ImplementedAuthenticationService implements AuthenticationService
{
	@Inject
	private UserDAO userDAO;

	@Override
	public UserSessionDTO authenticate(UserDTO user) throws SQLException
	{
		List<User> users = this.userDAO.getAllMatching(user.user);

		if ( users.size() <= 0 ) throw new NoUserFoundException(user);

		var userToCheck = users.get(0);

		if ( !userToCheck.hasPassword(user.password) )
			throw new IncorrectPasswordException();

		userToCheck.saveSession();

		return userToCheck.getSession();
	}

	@Override
	public boolean isAuthenticated(String token) throws SQLException
	{
		return false;
	}
}
