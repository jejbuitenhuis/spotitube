package com.jejbuitenhuis.spotitube.playlist;

import java.sql.SQLException;

public interface PlaylistService
{
	PlaylistsDTO getAll(String owner) throws SQLException;
	PlaylistsDTO getAllMatching(String userToken, long id) throws SQLException;
	int createPlaylist(String userToken, PlaylistDTO newPlaylist) throws SQLException;
	void deletePlaylist(long id) throws SQLException;
	void updatePlaylistName(long id, PlaylistDTO updatedPlaylist) throws SQLException;
}
