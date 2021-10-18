package com.jejbuitenhuis.spotitube.track;

import java.sql.SQLException;

public interface TracksService
{
	TracksDTO getAllExcludingPlaylist(long excludedPlaylist) throws SQLException;
	TracksDTO getAllFromPlaylist(long playlist) throws SQLException;
	int addTrackToPlaylist(TrackDTO track, long playlist) throws SQLException;
	void deleteTrackFromPlaylist(long playlist, long track) throws SQLException;
}
