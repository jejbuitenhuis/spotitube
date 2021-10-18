package com.jejbuitenhuis.spotitube.track;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class TrackDTO
{
	public long id;
	public String title;
	public String performer;
	public long duration;
	public String album;
	public Long playcount;
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
		Long playcount,
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

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;

		TrackDTO trackDTO = (TrackDTO) obj;

		return id == trackDTO.id
			&& duration == trackDTO.duration
			&& offlineAvailable == trackDTO.offlineAvailable
			&& title.equals(trackDTO.title)
			&& performer.equals(trackDTO.performer)
			&& Objects.equals(album, trackDTO.album)
			&& Objects.equals(playcount, trackDTO.playcount)
			&& Objects.equals(publicationDate, trackDTO.publicationDate)
			&& Objects.equals(description, trackDTO.description);
	}
}
