package com.jejbuitenhuis.spotitube.track;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TracksServiceImpl implements TracksService
{
	private TrackDAO trackDAO;

	@Inject
	public void setTrackDAO(TrackDAO trackDAO)
	{
		this.trackDAO = trackDAO;
	}

	@Override
	public TracksDTO getAllExcluding(long excludedId) throws SQLException
	{
		var tracks = this.trackDAO.getAll();
		var tracksToExclude = new ArrayList<Long>();

		for (Track track : tracks)
			if ( track.isInPlaylist(excludedId) )
				tracksToExclude.add( track.getId() );

		var parsedTracks = tracks.stream()
			.filter( t -> !tracksToExclude.contains( t.getId() ) )
			.map( t -> new TrackDTO(
				t.getId(),
				t.getTitle(),
				t.getPerformer(),
				t.getDuration(),
				t.getAlbum(),
				t.getPlaycount(),
				t.getPublicationDate(),
				t.getDescription(),
				t.isOfflineAvailable()
			))
			.collect( Collectors.toList() );

		return new TracksDTO(parsedTracks);
	}
}
