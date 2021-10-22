package com.jejbuitenhuis.spotitube.playlist;

import com.jejbuitenhuis.spotitube.authentication.user.User;
import com.jejbuitenhuis.spotitube.authentication.usersession.UserSession;
import com.jejbuitenhuis.spotitube.authentication.usersession.UserSessionDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlaylistServiceImplTest
{
	private static final String userToken = "58feed5a-3656-4351-8427-4122c48da2f9";
	private static final String userName = "user-test";
	private static final User user = new User(userName, "test");
	private static final UserSession userSession = new UserSession(user, userToken);

	private PlaylistServiceImpl sut;
	private PlaylistDAO mockedPlaylist;
	private UserSessionDAO mockedUserSession;

	@BeforeEach
	void setup() throws SQLException
	{
		this.sut = new PlaylistServiceImpl();

		this.mockedPlaylist = Mockito.mock(PlaylistDAO.class);
		this.mockedUserSession = Mockito.mock(UserSessionDAO.class);

		this.sut.setPlaylistDAO(this.mockedPlaylist);
		this.sut.setSessionDAO(this.mockedUserSession);

		final var result = new ArrayList<UserSession>();
		result.add(userSession);
		Mockito.when( this.mockedUserSession.getAllMatching( Mockito.eq(userToken) ) )
			.thenReturn(result);
	}

	@Test
	void whenGeneratePlaylistsDTOIsCalledItShouldReturnACorrectPlaylistsDTO()
	{
		final var firstPlaylist = new Playlist(1, "test1", userName, 10);
		final var secondPlaylist = new Playlist(2, "test2", "test2", 10);
		var playlists = new ArrayList<Playlist>();
		playlists.add(firstPlaylist);
		playlists.add(secondPlaylist);

		final var parsedFirstPlaylist = new PlaylistDTO(1, "test1", true);
		final var parsedSecondPlaylist = new PlaylistDTO(2, "test2", false);
		var parsedPlaylists = new ArrayList<PlaylistDTO>();
		parsedPlaylists.add(parsedFirstPlaylist);
		parsedPlaylists.add(parsedSecondPlaylist);
		final long expectedLength = 20;
		final var expected = new PlaylistsDTO(parsedPlaylists, expectedLength);

		var result = this.sut.generatePlaylistsDTO(userSession, playlists);

		assertEquals(expected, result);
	}

	@Test
	void whenGetAllIsCalledItShouldReturnAllPlaylists() throws SQLException
	{
		final long id = 1;
		final String name = "test1";
		final long length = 10;
		final var playlist = new ArrayList<Playlist>();
		playlist.add( new Playlist(id, name, userName, length) );

		var expectedPlaylistDTOs = new ArrayList<PlaylistDTO>();
		expectedPlaylistDTOs.add( new PlaylistDTO(id, name, true) );
		var expected = new PlaylistsDTO(expectedPlaylistDTOs, length);

		Mockito.when( this.mockedPlaylist.getAll() )
			.thenReturn(playlist);

		var result = this.sut.getAll(userToken);

		Mockito.verify(this.mockedUserSession).getAllMatching( Mockito.eq(userToken) );
		Mockito.verify(this.mockedPlaylist).getAll();

		assertEquals(expected, result);
	}

	@Test
	void whenGetAllMatchingIsCalledItShouldReturnAllTheMatchingPlaylists() throws SQLException
	{
		final long id = 1;
		final String name = "test1";
		final long length = 10;
		final var playlist = new ArrayList<Playlist>();
		playlist.add( new Playlist(id, name, userName, length) );

		var expectedPlaylistDTOs = new ArrayList<PlaylistDTO>();
		expectedPlaylistDTOs.add( new PlaylistDTO(id, name, true) );
		var expected = new PlaylistsDTO(expectedPlaylistDTOs, length);

		Mockito.when( this.mockedPlaylist.getAllMatching( Mockito.eq(id) ) )
			.thenReturn(playlist);

		var result = this.sut.getAllMatching(userToken, id);

		Mockito.verify(this.mockedUserSession).getAllMatching( Mockito.eq(userToken) );
		Mockito.verify(this.mockedPlaylist).getAllMatching( Mockito.eq(id) );

		assertEquals(expected, result);
	}

	@Test
	void whenCreatePlaylistIsCalledItShouldCreateAPlaylist() throws SQLException
	{
		final int insertedId = 5;

		final var newPlaylist = new PlaylistDTO(1L, "test1", true);

		Mockito.when( this.mockedPlaylist.save( Mockito.anyString(), Mockito.anyString() ) )
			.thenReturn(insertedId);

		var result = this.sut.createPlaylist(userToken, newPlaylist);

		Mockito.verify(this.mockedUserSession).getAllMatching( Mockito.eq(userToken) );
		Mockito.verify(this.mockedPlaylist).save( Mockito.anyString(), Mockito.anyString() );

		assertEquals(insertedId, result);
	}

	@Test
	void whenDeleteIsCalledItShouldDeleteThePlaylist() throws SQLException
	{
		final long playlistId = 5;

		this.sut.deletePlaylist(playlistId);

		Mockito.verify(this.mockedPlaylist).delete( Mockito.eq(playlistId) );
	}

	@Test
	void whenUpdatePlaylistNameIsCalledItShouldUpdateThePlaylistsName() throws SQLException
	{
		final long playlistId = 5;
		final var playlist = new PlaylistDTO(playlistId, "test1", true);

		this.sut.updatePlaylistName(playlistId, playlist);

		Mockito.verify( this.mockedPlaylist).update( Mockito.eq(playlistId), Mockito.anyString() );
	}
}
