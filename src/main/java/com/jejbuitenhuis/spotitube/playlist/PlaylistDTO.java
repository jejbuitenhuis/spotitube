package com.jejbuitenhuis.spotitube.playlist;


public class PlaylistDTO
{
	public int id;
	public String name;
	public boolean owner;
	public final Object[] tracks = new Object[0];

	public PlaylistDTO(int id, String name, boolean owner)
	{
		this.id = id;
		this.name = name;
		this.owner = owner;
	}
}
