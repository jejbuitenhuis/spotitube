package com.jejbuitenhuis.spotitube.track;

import com.jejbuitenhuis.spotitube.util.database.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackDAO extends DAO<Track>
{
	private static final String QUERY_ALL
		= "SELECT * " +
			"FROM vw_tracks_by_playlist;";
	private static final String QUERY_ALL_MATCHING
		= "SELECT * " +
			"FROM tracks " +
			"WHERE id = ?;";

	@Override
	protected Track parse(ResultSet row) throws SQLException
	{
		return new Track(
			row.getLong("playlist_id"),
			row.getLong("track_id"),
			row.getString("title"),
			row.getString("performer"),
			row.getLong("duration"),
			row.getString("album"),
			row.getLong("playcount"),
			row.getString("publication_date"),
			row.getString("description"),
			row.getBoolean("offline_available")
		);
	}

	@Override
	protected String getQueryAll()
	{
		return QUERY_ALL;
	}

	@Override
	protected String getQueryAllMatching()
	{
		return QUERY_ALL_MATCHING;
	}

	@Override
	protected String getQuerySave()
	{
		return null;
	}

	@Override
	protected String getQueryDelete()
	{
		return null;
	}

	@Override
	protected String getQueryUpdate()
	{
		return null;
	}
}
