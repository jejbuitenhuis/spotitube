package com.jejbuitenhuis.spotitube.resources;

import com.jejbuitenhuis.spotitube.track.TracksService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

// NOTE: how do we divide "/tracks/" and "/playlists/{id}/*/"
@Path("/tracks/")
public class TrackResource
{
	private TracksService tracksService;

	@Inject
	public void setTracksService(TracksService service)
	{
		this.tracksService = service;
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTracks(
		@QueryParam("token") String userToken,
		@QueryParam("forPlaylist") long exclude ) throws SQLException
	{
		return Response.ok(
			this.tracksService.getAllExcludingPlaylist(exclude)
		).build();
	}
}
