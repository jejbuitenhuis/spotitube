package com.jejbuitenhuis.spotitube.track;

import java.sql.SQLException;

public interface TracksService
{
	TracksDTO getAllExcluding(long excludedId) throws SQLException;
}
