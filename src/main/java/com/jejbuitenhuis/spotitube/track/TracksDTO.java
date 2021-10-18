package com.jejbuitenhuis.spotitube.track;

import java.util.List;

public class TracksDTO
{
	public List<TrackDTO> tracks;

	public TracksDTO(List<TrackDTO> tracks)
	{
		this.tracks = tracks;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null || this.getClass() != obj.getClass()) return false;

		TracksDTO tracksDTO = (TracksDTO) obj;

		if ( this.tracks.size() != tracksDTO.tracks.size() )
			return false;

		for (int i = 0; i < this.tracks.size(); i++)
			if ( !this.tracks.get(i).equals( tracksDTO.tracks.get(i) ) )
				return false;

		return true;
	}
}
