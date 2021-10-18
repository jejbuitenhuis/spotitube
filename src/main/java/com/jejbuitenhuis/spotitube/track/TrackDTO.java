package com.jejbuitenhuis.spotitube.track;

import java.text.SimpleDateFormat;
import java.util.Date;

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
		Date publicationDate,
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
		if (publicationDate == null)
			this.publicationDate = null;
		else
			this.publicationDate = new SimpleDateFormat("MM-dd-yyyy")
				.format(publicationDate);
		this.description = description;
		this.offlineAvailable = offlineAvailable;
	}
}
