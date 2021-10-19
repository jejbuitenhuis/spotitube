package com.jejbuitenhuis.spotitube.playlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlaylistDAOTest
{
	PlaylistDAO sut;

	@BeforeEach
	void setup()
	{
		this.sut = new PlaylistDAO();
	}

	@Test
	void whenParseIsCalledItShouldReturnANewUserWithTheCorrectValues() throws SQLException
	{
		final int id = 1;
		final String name = "test1";
		final String owner = "test2";
		final long length = 10;
		final var expected = new Playlist(id, name, owner, length);

		var mockedResultSet = Mockito.mock(ResultSet.class);

		Mockito.when( mockedResultSet.getInt("id") )
			.thenReturn(id);
		Mockito.when( mockedResultSet.getString("name") )
			.thenReturn(name);
		Mockito.when( mockedResultSet.getString("owner") )
			.thenReturn(owner);
		Mockito.when( mockedResultSet.getLong("length") )
			.thenReturn(length);

		var result = this.sut.parse(mockedResultSet);

		Mockito.verify(mockedResultSet).getInt("id");
		Mockito.verify(mockedResultSet).getString("name");
		Mockito.verify(mockedResultSet).getString("owner");
		Mockito.verify(mockedResultSet).getLong("length");

		assertEquals(expected, result);
	}

	@Test
	void whenGetQueryAllIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "SELECT id, name, owner, length FROM vw_playlists;";

		var result = this.sut.getQueryAll();

		assertEquals(expected, result);
	}

	@Test
	void whenGetQueryAllMatchingIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "SELECT id, name, owner, length FROM vw_playlists WHERE id = ?;";

		var result = this.sut.getQueryAllMatching();

		assertEquals(expected, result);
	}

	@Test
	void whenGetQuerySaveIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "INSERT INTO playlists (name, owner) VALUES (?, ?);";

		var result = this.sut.getQuerySave();

		assertEquals(expected, result);
	}


	@Test
	void whenGetQueryDeleteIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "DELETE FROM playlists WHERE id = ?";

		var result = this.sut.getQueryDelete();

		assertEquals(expected, result);
	}

	@Test
	void whenGetQueryUpdateIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "UPDATE playlists SET name = ? WHERE id = ?";

		var result = this.sut.getQueryUpdate();

		assertEquals(expected, result);
	}
}