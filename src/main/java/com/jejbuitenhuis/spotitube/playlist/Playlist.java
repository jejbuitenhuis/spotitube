package com.jejbuitenhuis.spotitube.playlist;

public class Playlist
{
	private int id;
	private String name, owner;
	private long length;

	public Playlist(int id, String name, String owner, long length)
	{
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.length = length;
	}

	public int getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.name;
	}

	public boolean isOwner(String user)
	{
		return this.owner.equals(user);
	}

	public long getLength()
	{
		return this.length;
	}
}
