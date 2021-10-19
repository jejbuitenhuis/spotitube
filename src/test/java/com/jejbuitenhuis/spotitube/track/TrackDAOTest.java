package com.jejbuitenhuis.spotitube.track;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
