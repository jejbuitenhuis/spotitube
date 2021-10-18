package com.jejbuitenhuis.spotitube.track;

public class TrackDTO
{
	public long id;
	public String title;
	public String performer;
	public long duration;
	public String album;
	public long playcount;
	public String publicationDate;
	public String description;
	public boolean offlineAvailable;

	public TrackDTO() {}

	public TrackDTO(
		long id,
		String title,
		String performer,
		long duration,
		String album,
		long playcount,
		String publicationDate,
		String description,
		boolean offlineAvailable
	)
	{
		this.id = id;
		this.title = title;
		this.performer = performer;
		this.duration = duration;
		this.album = album;
		this.playcount = playcount;
		this.publicationDate = publicationDate;
		this.description = description;
		this.offlineAvailable = offlineAvailable;
	}
}
