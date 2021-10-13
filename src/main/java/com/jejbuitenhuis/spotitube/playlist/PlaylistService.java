package com.jejbuitenhuis.spotitube.playlist;

import java.sql.SQLException;

public interface PlaylistService
{
	PlaylistsDTO getAll(String owner) throws SQLException;
	PlaylistsDTO getAllMatching(String userToken, long id) throws SQLException;
}
