package com.jejbuitenhuis.spotitube.resources;

import com.jejbuitenhuis.spotitube.playlist.PlaylistDTO;
import com.jejbuitenhuis.spotitube.playlist.PlaylistService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.sql.SQLException;

@Path("/playlists/")
public class PlaylistResource
{
	private PlaylistService playlistService;

	@Inject
	public void setPlaylistService(PlaylistService resource)
	{
		this.playlistService = resource;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPlaylists(@QueryParam("token") String userToken) throws SQLException
	{
		var playlists = this.playlistService.getAll(userToken);

		return Response.ok(playlists).build();
	}

	@GET
	@Path("/{id}/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPlaylistsMatching(
			@QueryParam("token") String userToken, @PathParam("id") long id) throws SQLException
	{
		var playlists = this.playlistService.getAllMatching(userToken, id);

		return Response.ok(playlists).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPlaylist(
			@QueryParam("token") String userToken, PlaylistDTO newPlaylist) throws SQLException
	{
		var createdId = this.playlistService.createPlaylist(userToken, newPlaylist);
		var playlists = this.playlistService.getAll(userToken);

		return Response.created( URI.create(
			String.format("/playlists/%d/", createdId) )
		)
			.entity(playlists)
			.build();
	}
}
