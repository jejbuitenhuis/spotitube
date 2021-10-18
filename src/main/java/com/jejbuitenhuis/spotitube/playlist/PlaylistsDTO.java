package com.jejbuitenhuis.spotitube.playlist;

import java.util.List;

public class PlaylistsDTO
{
	public List<PlaylistDTO> playlists;
	public long length;

	public PlaylistsDTO(List<PlaylistDTO> playlists, long length)
	{
		this.playlists = playlists;
		this.length = length;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null || this.getClass() != obj.getClass()) return false;

		PlaylistsDTO playlistsDTO = (PlaylistsDTO) obj;

		if ( this.playlists.size() != playlistsDTO.playlists.size() )
			return false;

		for (int i = 0; i < this.playlists.size(); i++)
			if ( !this.playlists.get(i).equals( playlistsDTO.playlists.get(i) ) )
				return false;

		return this.length == playlistsDTO.length;
	}
}
