package com.jejbuitenhuis.spotitube.playlist;


import java.util.Arrays;

public class PlaylistDTO
{
	public long id;
	public String name;
	public boolean owner;
	public final Object[] tracks = new Object[0];

	public PlaylistDTO() {}

	public PlaylistDTO(long id, String name, boolean owner)
	{
		this.id = id;
		this.name = name;
		this.owner = owner;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PlaylistDTO that = (PlaylistDTO) o;

		return id == that.id
			&& owner == that.owner
			&& name.equals(that.name)
			&& Arrays.equals(tracks, that.tracks);
	}
}
