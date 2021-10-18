package com.jejbuitenhuis.spotitube.resources;

import com.jejbuitenhuis.spotitube.playlist.PlaylistDTO;
import com.jejbuitenhuis.spotitube.playlist.PlaylistService;
import com.jejbuitenhuis.spotitube.playlist.PlaylistsDTO;
import com.jejbuitenhuis.spotitube.track.TrackDTO;
import com.jejbuitenhuis.spotitube.track.TracksDTO;
import com.jejbuitenhuis.spotitube.track.TracksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlaylistResourceTest
{
	private PlaylistResource sut;
	private PlaylistService mockedPlaylist;
	private TracksService mockedTracks;

	@BeforeEach
	void setup()
	{
		this.sut = new PlaylistResource();

		this.mockedPlaylist = Mockito.mock(PlaylistService.class);
		this.mockedTracks = Mockito.mock(TracksService.class);

		this.sut.setPlaylistService(this.mockedPlaylist);
		this.sut.setTracksService(this.mockedTracks);
	}

	@Test
	void whenGetAllPlaylistsIsCalledItShouldReturnAListOfPlaylists() throws SQLException
	{
		var playlistDTOs = new ArrayList<PlaylistDTO>();
		playlistDTOs.add( new PlaylistDTO(2, "test", false) );
		var playlistsDTO = new PlaylistsDTO(playlistDTOs, 512);

		Mockito.when( this.mockedPlaylist.getAll( Mockito.anyString() ) )
			.thenReturn(playlistsDTO);

		var result = this.sut.getAllPlaylists("test");

		Mockito.verify(this.mockedPlaylist).getAll( Mockito.anyString() );

		assertEquals( playlistsDTO, result.getEntity() );
	}

	@Test
	void whenGetAllPlaylistsMatchingIsCalledItShouldReturnAListOfPlaylistsMatching() throws SQLException
	{
		var playlistDTOs = new ArrayList<PlaylistDTO>();
		playlistDTOs.add( new PlaylistDTO(2, "test", false) );
		var playlistsDTO = new PlaylistsDTO(playlistDTOs, 512);

		Mockito.when( this.mockedPlaylist.getAllMatching( Mockito.anyString(), Mockito.anyLong() ) )
			.thenReturn(playlistsDTO);

		var result = this.sut.getAllPlaylistsMatching("test", 2);

		assertEquals( playlistsDTO, result.getEntity() );
	}

	@Test
	void whenCreatePlaylistIsCalledItShouldReturnAListOfPlaylistsAndTheCorrectURI() throws SQLException
	{
		final int createdId = 3;
		var playlistDTOs = new ArrayList<PlaylistDTO>();
		playlistDTOs.add( new PlaylistDTO(2, "test", false) );
		var playlistsDTO = new PlaylistsDTO(playlistDTOs, 512);

		Mockito.when( this.mockedPlaylist.createPlaylist( Mockito.anyString(), Mockito.any(PlaylistDTO.class) ) )
			.thenReturn(createdId);
		Mockito.when( this.mockedPlaylist.getAll( Mockito.anyString() ) )
			.thenReturn(playlistsDTO);

		var result = this.sut.createPlaylist( "test", playlistDTOs.get(0) );

		assertEquals( playlistsDTO, result.getEntity() );
		assertTrue( result.getHeaders().size() > 0 );
		assertEquals(
			String.format("/playlists/%d/", createdId),
			result.getStringHeaders().get("Location").get(0)
		);
	}

	@Test
	void whenDeletePlaylistIsCalledItShouldDeleteAPlaylistAndReturnAListOfPlaylists() throws SQLException
	{
		var playlistDTOs = new ArrayList<PlaylistDTO>();
		playlistDTOs.add( new PlaylistDTO(2, "test", false) );
		var playlistsDTO = new PlaylistsDTO(playlistDTOs, 512);

		Mockito.when( this.mockedPlaylist.getAll( Mockito.anyString() ) )
			.thenReturn(playlistsDTO);

		var result = this.sut.deletePlaylist("test", 3);

		Mockito.verify(this.mockedPlaylist).deletePlaylist( Mockito.anyLong() );
		Mockito.verify(this.mockedPlaylist).getAll( Mockito.anyString() );

		assertEquals( playlistsDTO, result.getEntity() );
	}

	@Test
	void whenUpdatePlaylistNameIsCalledItShouldUpdateThePlaylistsNameAndReturnAListOfPlaylists() throws SQLException
	{
		var playlistDTOs = new ArrayList<PlaylistDTO>();
		playlistDTOs.add(new PlaylistDTO(2, "test", false));
		var playlistsDTO = new PlaylistsDTO(playlistDTOs, 512);

		Mockito.when(this.mockedPlaylist.getAll(Mockito.anyString()))
				.thenReturn(playlistsDTO);

		var result = this.sut.updatePlaylistName( "test", 2, playlistDTOs.get(0) );

		Mockito.verify(this.mockedPlaylist).updatePlaylistName( Mockito.anyLong(), Mockito.any(PlaylistDTO.class) );
		Mockito.verify(this.mockedPlaylist).getAll( Mockito.anyString() );

		assertEquals(playlistsDTO, result.getEntity());
	}

	@Test
	void whenGetTracksFromPlaylistIsCalledItShouldReturnAListOfTracksInThePlaylist() throws SQLException
	{
		var trackDTOs = new ArrayList<TrackDTO>();
		trackDTOs.add( new TrackDTO(
				2,
				"test",
				"performer",
				2,
				"album",
				null,
				null,
				null,
				false
		) );
		var tracksDTO = new TracksDTO(trackDTOs);

		Mockito.when( this.mockedTracks.getAllFromPlaylist( Mockito.anyLong() ) )
			.thenReturn(tracksDTO);

		var result = this.sut.getTracksFromPlaylist(1);

		Mockito.verify(this.mockedTracks).getAllFromPlaylist( Mockito.anyLong() );

		assertEquals( tracksDTO, result.getEntity() );
	}

	@Test
	void whenAddTrackToPlaylistIsCalledItShouldReturnAListOfTracksInThePlaylistAndACorrectURI() throws SQLException
	{
		final int playlistId = 4;
		final int trackId = 5;
		var trackDTOs = new ArrayList<TrackDTO>();
		trackDTOs.add( new TrackDTO(
			2,
			"test",
			"performer",
			2,
			"album",
			null,
			null,
			null,
			false
		) );
		var tracksDTO = new TracksDTO(trackDTOs);

		Mockito.when( this.mockedTracks.addTrackToPlaylist( Mockito.any(TrackDTO.class), Mockito.anyLong() ) )
			.thenReturn(trackId);
		Mockito.when( this.mockedTracks.getAllFromPlaylist( Mockito.anyLong() ) )
			.thenReturn(tracksDTO);

		var result = this.sut.addTrackToPlaylist( playlistId, trackDTOs.get(0) );

		Mockito.verify(this.mockedTracks).addTrackToPlaylist( Mockito.any(TrackDTO.class), Mockito.anyLong() );
		Mockito.verify(this.mockedTracks).getAllFromPlaylist( Mockito.anyLong() );

		assertEquals(
			String.format("/playlists/%d/tracks/%d/", playlistId, trackId),
			result.getStringHeaders().get("Location").get(0)
		);
		assertEquals( tracksDTO, result.getEntity() );
	}

	@Test
	void whenDeleteTrackFromPlaylistIsCalledItShouldDeleteTheTrackAndReturnAListOfTracksInThePlaylist() throws SQLException
	{
		var trackDTOs = new ArrayList<TrackDTO>();
		trackDTOs.add( new TrackDTO(
				2,
				"test",
				"performer",
				2,
				"album",
				null,
				null,
				null,
				false
		) );
		var tracksDTO = new TracksDTO(trackDTOs);

		Mockito.when( this.mockedTracks.getAllFromPlaylist( Mockito.anyLong() ) )
			.thenReturn(tracksDTO);

		var result = this.sut.deleteTrackFromPlaylist(1, 3);

		Mockito.verify(this.mockedTracks).deleteTrackFromPlaylist( Mockito.anyLong(), Mockito.anyLong() );
		Mockito.verify(this.mockedTracks).getAllFromPlaylist( Mockito.anyLong() );

		assertEquals( tracksDTO, result.getEntity() );
	}
}
