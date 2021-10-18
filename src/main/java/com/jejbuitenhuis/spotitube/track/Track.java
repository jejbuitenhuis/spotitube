package com.jejbuitenhuis.spotitube.track;


public class Track
{
	private Long playlistId;
	private long id;
	private String title;
	private String performer;
	private long duration;
	private String album;
	private Long playcount;
	private String publicationDate;
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
		this.publicationDate = publicationDate;
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

	public String getPublicationDate()
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
}
