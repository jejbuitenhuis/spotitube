package com.jejbuitenhuis.spotitube.authentication;

import com.jejbuitenhuis.spotitube.util.exceptions.authentication.IncorrectPasswordException;
import com.jejbuitenhuis.spotitube.util.exceptions.authentication.NoUserFoundException;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

public class ImplementedAuthenticationService implements AuthenticationService
{
	private UserDAO userDAO;
	private UserSessionDAO sessionDAO;

	@Inject
	public void setUserDAO(UserDAO userDAO)
	{
		this.userDAO = userDAO;
	}

	@Inject
	public void setSessionDAO(UserSessionDAO sessionDAO)
	{
		this.sessionDAO = sessionDAO;
	}

	@Override
	public UserSessionDTO authenticate(UserDTO user) throws SQLException
	{
		List<User> users = this.userDAO.getAllMatching(user.user);

		if ( users.size() <= 0 ) throw new NoUserFoundException(user);

		var userToCheck = users.get(0);

		if ( !userToCheck.hasPassword(user.password) )
			throw new IncorrectPasswordException();

		var session = userToCheck.createSession();

		this.sessionDAO.save(session);

		return new UserSessionDTO(
			session.getUser(),
			session.getToken().toString()
		);
	}

	@Override
	public boolean isAuthenticated(String token) throws SQLException
	{
		return false;
	}
}
