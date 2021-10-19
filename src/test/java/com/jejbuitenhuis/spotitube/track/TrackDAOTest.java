package com.jejbuitenhuis.spotitube.track;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackDAOTest
{
	TrackDAO sut;

	@BeforeEach
	void setup()
	{
		this.sut = new TrackDAO();
	}

	@Test
	void whenParseIsCalledItShouldReturnANewTrackWithTheCorrectValues() throws SQLException
	{
		final long playlistId = 1;
		final long trackId = 2;
		final String title = "test1";
		final String performer = "test2";
		final long duration = 512;
		final String album = "test3";
		final long playcount = 0;
		final String publicationDate = null;
		final String description = null;
		final boolean offlineAvailable = false;
		final var expected = new Track(
			playlistId,
			trackId,
			title,
			performer,
			duration,
			album,
			playcount,
			publicationDate,
			description,
			offlineAvailable
		);

		var mockedResultSet = Mockito.mock(ResultSet.class);

		Mockito.when( mockedResultSet.getLong("playlist_id") )
			.thenReturn(playlistId);
		Mockito.when( mockedResultSet.getLong("track_id") )
			.thenReturn(trackId);
		Mockito.when( mockedResultSet.getString("title") )
			.thenReturn(title);
		Mockito.when( mockedResultSet.getString("performer") )
			.thenReturn(performer);
		Mockito.when( mockedResultSet.getLong("duration") )
			.thenReturn(duration);
		Mockito.when( mockedResultSet.getString("album") )
			.thenReturn(album);
		Mockito.when( mockedResultSet.getLong("playcount") )
			.thenReturn(playcount);
		Mockito.when( mockedResultSet.getString("publication_date") )
			.thenReturn(publicationDate);
		Mockito.when( mockedResultSet.getString("description") )
			.thenReturn(description);
		Mockito.when( mockedResultSet.getBoolean("offline_available") )
			.thenReturn(offlineAvailable);

		var result = this.sut.parse(mockedResultSet);

		Mockito.verify(mockedResultSet).getLong("playlist_id");
		Mockito.verify(mockedResultSet).getLong("track_id");
		Mockito.verify(mockedResultSet).getString("title");
		Mockito.verify(mockedResultSet).getString("performer");
		Mockito.verify(mockedResultSet).getLong("duration");
		Mockito.verify(mockedResultSet).getString("album");
		Mockito.verify(mockedResultSet).getLong("playcount");
		Mockito.verify(mockedResultSet).getString("publication_date");
		Mockito.verify(mockedResultSet).getString("description");
		Mockito.verify(mockedResultSet).getBoolean("offline_available");

		assertEquals(expected, result);
	}

	@Test
	void whenGetQueryAllIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "SELECT * FROM vw_tracks_by_playlist;";

		var result = this.sut.getQueryAll();

		assertEquals(expected, result);
	}

	@Test
	void whenGetQueryAllMatchingIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "SELECT * FROM vw_tracks_by_playlist WHERE track_id = ? GROUP BY track_id;";

		var result = this.sut.getQueryAllMatching();

		assertEquals(expected, result);
	}

	@Test
	void whenGetQuerySaveIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "INSERT INTO playlist_tracks (playlist_id, track_id) VALUES (?, ?);";

		var result = this.sut.getQuerySave();

		assertEquals(expected, result);
	}

	@Test
	void whenGetQueryDeleteIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "DELETE FROM playlist_tracks WHERE playlist_id = ? AND track_id = ?;";

		var result = this.sut.getQueryDelete();

		assertEquals(expected, result);
	}

	@Test
	void whenGetQueryUpdateIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "UPDATE tracks SET offline_available = ? WHERE id = ?";

		var result = this.sut.getQueryUpdate();

		assertEquals(expected, result);
	}
}
