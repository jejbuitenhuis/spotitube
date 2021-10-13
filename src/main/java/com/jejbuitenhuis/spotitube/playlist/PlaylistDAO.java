package com.jejbuitenhuis.spotitube.playlist;

import com.jejbuitenhuis.spotitube.util.database.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistDAO extends DAO<Playlist>
{
	private static final String QUERY_GET_ALL
		= "SELECT id, name, owner, length " +
			"FROM vw_playlists;";
	private static final String QUERY_GET_ALL_MATCHING
		= "SELECT id, name, owner, length " +
			"FROM vw_playlists " +
			"WHERE id = ?;";
	private static final String QUERY_SAVE = "";

	@Override
	protected Playlist parse(ResultSet row) throws SQLException
	{
		return new Playlist(
			row.getInt("id"),
			row.getString("name"),
			row.getString("owner"),
			row.getLong("length")
		);
	}

	@Override
	protected String getQueryAll()
	{
		return QUERY_GET_ALL;
	}

	@Override
	protected String getQueryAllMatching()
	{
		return QUERY_GET_ALL_MATCHING;
	}

	@Override
	protected String getQuerySave()
	{
		return QUERY_SAVE;
	}
}
