package com.jejbuitenhuis.spotitube.authentication;

import com.jejbuitenhuis.spotitube.authentication.user.User;
import com.jejbuitenhuis.spotitube.authentication.user.UserDAO;
import com.jejbuitenhuis.spotitube.authentication.user.UserDTO;
import com.jejbuitenhuis.spotitube.authentication.usersession.UserSession;
import com.jejbuitenhuis.spotitube.authentication.usersession.UserSessionDAO;
import com.jejbuitenhuis.spotitube.authentication.usersession.UserSessionDTO;
import com.jejbuitenhuis.spotitube.util.exceptions.authentication.IncorrectPasswordException;
import com.jejbuitenhuis.spotitube.util.exceptions.authentication.NoUserFoundException;

import javax.inject.Inject;
import java.util.List;

public class AuthenticationServiceImpl implements AuthenticationService
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
	public UserSessionDTO authenticate(UserDTO user)
	{
		List<User> users = this.userDAO.getAllMatching(user.user);

		if ( users.size() <= 0 ) throw new NoUserFoundException(user);

		var userToCheck = users.get(0);

		if ( !userToCheck.hasPassword(user.password) )
			throw new IncorrectPasswordException();

		var session = userToCheck.createSession();

		this.sessionDAO.save(session);

		return new UserSessionDTO(
			session.getUser().getUsername(),
			session.getToken().toString()
		);
	}

	@Override
	public boolean isAuthenticated(String token)
	{
		List<UserSession> sessions = this.sessionDAO.getAllMatching(token);

		return sessions.size() > 0;
	}
}
