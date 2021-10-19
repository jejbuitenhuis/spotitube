package com.jejbuitenhuis.spotitube.track;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Track
{
	private final Logger logger = Logger.getLogger( this.getClass().getName() );

	private Long playlistId;
	private long id;
	private String title;
	private String performer;
	private long duration;
	private String album;
	private Long playcount;
	private Date publicationDate;
	private String description;
	private boolean offlineAvailable;

	public Track(
		Long playlistId,
		long id,
		String title,
		String performer,
		long duration,
		String album,
		Long playcount,
		String publicationDate,
		String description,
		boolean offlineAvailable
	)
	{
		this.playlistId = playlistId;
		this.id = id;
		this.title = title;
		this.performer = performer;
		this.duration = duration;
		this.album = album;
		this.playcount = playcount;
		try
		{
			if (publicationDate == null)
				this.publicationDate = null;
			else
				this.publicationDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
					.parse(publicationDate);
		} catch (ParseException e)
		{
			this.logger.log(
				Level.SEVERE,
				"Error parsing Track publication date",
				e
			);
			this.publicationDate = new Date(0);
		}
		this.description = description;
		this.offlineAvailable = offlineAvailable;
	}

	public Long getPlaylistId()
	{
		return this.playlistId;
	}

	public long getId()
	{
		return this.id;
	}

	public String getTitle()
	{
		return this.title;
	}

	public String getPerformer()
	{
		return this.performer;
	}

	public long getDuration()
	{
		return this.duration;
	}

	public String getAlbum()
	{
		return this.album;
	}

	public Long getPlaycount()
	{
		return this.playcount;
	}

	public Date getPublicationDate()
	{
		return this.publicationDate;
	}

	public String getDescription()
	{
		return this.description;
	}

	public boolean isOfflineAvailable()
	{
		return this.offlineAvailable;
	}

	public boolean isInPlaylist(long playlistId)
	{
		return this.playlistId != null
			&& this.playlistId == playlistId;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;

		Track track = (Track) obj;

		return id == track.id
			&& duration == track.duration
			&& offlineAvailable == track.offlineAvailable
			&& playlistId.equals(track.playlistId)
			&& title.equals(track.title)
			&& performer.equals(track.performer)
			&& Objects.equals(album, track.album)
			&& Objects.equals(playcount, track.playcount)
			&& Objects.equals(publicationDate, track.publicationDate)
			&& Objects.equals(description, track.description);
	}
}
