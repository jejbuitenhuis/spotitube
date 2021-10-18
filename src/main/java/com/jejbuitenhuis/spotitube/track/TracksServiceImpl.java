package com.jejbuitenhuis.spotitube.track;

import com.jejbuitenhuis.spotitube.util.exceptions.track.TrackNotFoundException;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TracksServiceImpl implements TracksService
{
	private TrackDAO trackDAO;

	@Inject
	public void setTrackDAO(TrackDAO trackDAO)
	{
		this.trackDAO = trackDAO;
	}

	protected List<Track> getAllTracksInPlaylist(List<Track> tracks, long playlist)
	{
		var hits = new ArrayList<Track>();

		for (Track track : tracks)
			if ( track.isInPlaylist(playlist) )
				hits.add(track);

		return hits;
	}

	protected TracksDTO generateTracksDTO(List<Track> tracks)
	{
		var trackDTOs = tracks.stream()
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

		return new TracksDTO(trackDTOs);
	}

	@Override
	public TracksDTO getAllExcludingPlaylist(long excludedPlaylist) throws SQLException
	{
		var tracks = this.trackDAO.getAll();
		var tracksToExclude = new ArrayList<Long>();

		for (Track track : tracks)
			if ( track.isInPlaylist(excludedPlaylist) )
				tracksToExclude.add( track.getId() );

		var parsedTracks = tracks.stream()
			.filter( t -> !tracksToExclude.contains( t.getId() ) )
			.collect( Collectors.toList() );

		return this.generateTracksDTO(parsedTracks);
	}

	@Override
	public TracksDTO getAllFromPlaylist(long playlist) throws SQLException
	{
		var tracks = this.trackDAO.getAll();
		var playlistTracks = this.getAllTracksInPlaylist(tracks, playlist);

		return this.generateTracksDTO(playlistTracks);
	}

	@Override
	public int addTrackToPlaylist(TrackDTO track, long playlist) throws SQLException
	{
		var hits = this.trackDAO.getAllMatching(track.id);

		if ( hits.size() <= 0) throw new TrackNotFoundException();

		var hit = hits.get(0);

		var insertedId = this.trackDAO.save( playlist, hit.getId() );
		this.trackDAO.update( hit.getId(), track.offlineAvailable ? 1 : 0 );

		return insertedId;
	}

	@Override
	public void deleteTrackFromPlaylist(long playlist, long track) throws SQLException
	{
		this.trackDAO.delete(playlist, track);
	}
}
