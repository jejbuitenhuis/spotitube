package com.jejbuitenhuis.spotitube.resources;

import com.jejbuitenhuis.spotitube.playlist.PlaylistService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/playlists/")
public class PlaylistResource
{
	private PlaylistService playlistResource;

	@Inject
	public void setPlaylistResource(PlaylistService resource)
	{
		this.playlistResource = resource;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPlaylists(@QueryParam("token") String userToken) throws SQLException
	{
		var playlists = this.playlistResource.getAll(userToken);

		return Response.ok(playlists).build();
	}

	@GET
	@Path("/{id}/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllPlaylistsMatching(
			@QueryParam("token") String userToken, @PathParam("id") long id) throws SQLException
	{
		var playlists = this.playlistResource.getAllMatching(userToken, id);

		return Response.ok(playlists).build();
	}
}
