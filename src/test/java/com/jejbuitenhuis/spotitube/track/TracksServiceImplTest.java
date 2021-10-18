package com.jejbuitenhuis.spotitube.track;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TracksServiceImplTest
{
	private TracksServiceImpl sut;
	private TrackDAO mockedDAO;

	@BeforeEach
	void setup()
	{
		this.sut = new TracksServiceImpl();

		this.mockedDAO = Mockito.mock(TrackDAO.class);

		this.sut.setTrackDAO(this.mockedDAO);
	}

	@Test
	void whenGetAllTracksInPlaylistIsCalledItShouldFilterOutAllTheIncorrectTracks()
	{
		final long correctPlaylist = 1;
		final var correctTrack = new Track(
			correctPlaylist,
			1,
			"test",
			"performer",
			512,
			"album",
			null,
			null,
			null,
			false
		);
		final var incorrectTrack = new Track(
			2L,
			2,
			"test",
			"performer",
			512,
			"album",
			null,
			null,
			null,
			false
		);
		var tracks = new ArrayList<Track>();
		tracks.add(correctTrack);
		tracks.add(incorrectTrack);
		var expectedTracks = new ArrayList<Track>();
		expectedTracks.add(correctTrack);

		var result = this.sut.getAllTracksInPlaylist(tracks, correctPlaylist);

		assertEquals( expectedTracks, result );
	}

	@Test
	void whenGenerateTracksDTOIsCalledItShouldCreateATracksDTO()
	{
		final var firstTrack = new Track(
				1L,
				1,
				"test",
				"performer",
				512,
				"album",
				null,
				null,
				null,
				false
		);
		final var secondTrack = new Track(
				2L,
				2,
				"test",
				"performer",
				512,
				"album",
				null,
				null,
				null,
				false
		);
		var tracks = new ArrayList<Track>();
		tracks.add(firstTrack);
		tracks.add(secondTrack);

		var parsedTracks = new ArrayList<TrackDTO>();
		parsedTracks.add( new TrackDTO(
			1,
			"test",
			"performer",
			512,
			"album",
			null,
			null,
			null,
			false
		) );
		parsedTracks.add( new TrackDTO(
			2,
			"test",
			"performer",
			512,
			"album",
			null,
			null,
			null,
			false
		) );

		var expected = new TracksDTO(parsedTracks);

		var result = this.sut.generateTracksDTO(tracks);

		assertEquals(expected, result);
	}

	@Test
	void whenGetAllExcludingPlaylistIsCalledItShouldReturnAllTheTracksExcludingTheOnesInTheExcludedPlaylist() throws SQLException
	{
		final long excludedPlaylist = 1;
		final var firstTrack = new Track(
			excludedPlaylist,
			1,
			"test",
			"performer",
			512,
			"album",
			null,
			null,
			null,
			false
		);
		final var secondTrack = new Track(
			2L,
			2,
			"test",
			"performer",
			512,
			"album",
			null,
			null,
			null,
			false
		);
		var tracks = new ArrayList<Track>();
		tracks.add(firstTrack);
		tracks.add(secondTrack);

		var parsedTrackDTOs = new ArrayList<TrackDTO>();
		parsedTrackDTOs.add( new TrackDTO(
			2,
			"test",
			"performer",
			512,
			"album",
			null,
			null,
			null,
			false
		) );
		var expected = new TracksDTO(parsedTrackDTOs);

		Mockito.when( this.mockedDAO.getAll() )
			.thenReturn(tracks);

		var result = this.sut.getAllExcludingPlaylist(excludedPlaylist);

		Mockito.verify(this.mockedDAO).getAll();

		assertEquals(expected, result);
	}

	@Test
	void whenGetAllFromPlaylistIsCalledItShouldReturnAllTheTracksInThePlaylists() throws SQLException
	{
		final long wantedPlaylist = 1;
		final var firstTrack = new Track(
			wantedPlaylist,
			1,
			"test",
			"performer",
			512,
			"album",
			null,
			null,
			null,
			false
		);
		final var secondTrack = new Track(
			2L,
			2,
			"test",
			"performer",
			512,
			"album",
			null,
			null,
			null,
			false
		);
		var tracks = new ArrayList<Track>();
		tracks.add(firstTrack);
		tracks.add(secondTrack);

		var parsedTrackDTOs = new ArrayList<TrackDTO>();
		parsedTrackDTOs.add( new TrackDTO(
			1,
			"test",
			"performer",
			512,
			"album",
			null,
			null,
			null,
			false
		) );
		var expected = new TracksDTO(parsedTrackDTOs);

		Mockito.when( this.mockedDAO.getAll() )
			.thenReturn(tracks);

		var result = this.sut.getAllFromPlaylist(wantedPlaylist);

		Mockito.verify(this.mockedDAO).getAll();

		assertEquals(expected, result);
	}

	@Test
	void whenAddTrackToPlaylistIsCalledItShouldAddTheTrackToThePlaylist() throws SQLException
	{
		final long playlistId = 1;
		final int insertedId = 2;
		final var firstTrack = new Track(
			playlistId,
			2,
			"test",
			"performer",
			512,
			"album",
			null,
			null,
			null,
			false
		);
		var tracks = new ArrayList<Track>();
		tracks.add(firstTrack);

		final var trackToAdd = new TrackDTO(
			2,
			"test",
			"performer",
			512,
			"album",
			null,
			null,
			null,
			false
		);

		Mockito.when( this.mockedDAO.getAllMatching( Mockito.anyLong() ) )
			.thenReturn(tracks);
		Mockito.when( this.mockedDAO.save( Mockito.anyLong(), Mockito.anyLong() ) )
			.thenReturn(insertedId);

		var result = this.sut.addTrackToPlaylist(trackToAdd, playlistId);

		Mockito.verify(this.mockedDAO).getAllMatching( Mockito.anyLong() );
		Mockito.verify(this.mockedDAO).save( Mockito.anyLong(), Mockito.anyLong() );
		Mockito.verify(this.mockedDAO).update( Mockito.anyLong(), Mockito.anyInt() );

		assertEquals(insertedId, result);
	}

	@Test
	void whenDeleteTrackFromPlaylistIsCalledItShouldDeleteThePlaylist() throws SQLException
	{
		this.sut.deleteTrackFromPlaylist(1L, 1L);

		Mockito.verify(this.mockedDAO).delete( Mockito.anyLong(), Mockito.anyLong() );
	}
}
