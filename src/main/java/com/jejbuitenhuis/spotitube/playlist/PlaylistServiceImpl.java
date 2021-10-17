package com.jejbuitenhuis.spotitube.playlist;

import com.jejbuitenhuis.spotitube.authentication.usersession.UserSession;
import com.jejbuitenhuis.spotitube.authentication.usersession.UserSessionDAO;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PlaylistServiceImpl implements PlaylistService
{
	private PlaylistDAO playlistDAO;
	private UserSessionDAO sessionDAO;

	@Inject
	public void setPlaylistDAO(PlaylistDAO playlistDAO)
	{
		this.playlistDAO = playlistDAO;
	}

	@Inject
	public void setSessionDAO(UserSessionDAO sessionDAO)
	{
		this.sessionDAO = sessionDAO;
	}

	private PlaylistsDTO generatePlaylistsDTO(UserSession session, List<Playlist> playlists)
	{
		List<PlaylistDTO> parsedPlaylists = playlists.stream()
			.map(p -> new PlaylistDTO(
				p.getId(),
				p.getName(),
				p.isOwner( session.getUser() )
			))
			.collect( Collectors.toList() );
		long length = playlists.stream()
			.mapToLong(Playlist::getLength)
			.sum();

		return new PlaylistsDTO(
			parsedPlaylists,
			length
		);
	}

	@Override
	public PlaylistsDTO getAll(String userToken) throws SQLException
	{
		var session = this.sessionDAO.getAllMatching(userToken)
			.get(0);

		List<Playlist> playlists = this.playlistDAO.getAll();

		return this.generatePlaylistsDTO(session, playlists);
	}

	@Override
	public PlaylistsDTO getAllMatching(String userToken, long id) throws SQLException
	{
		var session = this.sessionDAO.getAllMatching(userToken)
			.get(0);

		List<Playlist> playlists = this.playlistDAO.getAllMatching(id);

		return this.generatePlaylistsDTO(session, playlists);
	}

	@Override
	public int createPlaylist(String userToken, PlaylistDTO newPlaylist) throws SQLException
	{
		var session = this.sessionDAO.getAllMatching(userToken)
			.get(0);

		int insertedId = this.playlistDAO.save(
			newPlaylist.name,
			session.getUser()
		);

		return insertedId;
	}
}
