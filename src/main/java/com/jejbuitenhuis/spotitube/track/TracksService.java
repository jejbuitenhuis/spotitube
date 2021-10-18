package com.jejbuitenhuis.spotitube.track;

import java.sql.SQLException;

public interface TracksService
{
	TracksDTO getAllExcludingPlaylist(long excludedPlaylist) throws SQLException;
	TracksDTO getAllFromPlaylist(long playlist) throws SQLException;
}
