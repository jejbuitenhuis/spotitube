package com.jejbuitenhuis.spotitube.resources;

import com.jejbuitenhuis.spotitube.track.TrackDTO;
import com.jejbuitenhuis.spotitube.track.TracksDTO;
import com.jejbuitenhuis.spotitube.track.TracksService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrackResourceTest
{
	private TrackResource sut;
	private TracksService mockedService;

	@BeforeEach
	void setup()
	{
		this.sut = new TrackResource();

		this.mockedService = Mockito.mock(TracksService.class);

		this.sut.setTracksService(this.mockedService);
	}

	@Test
	void whenGetTracksIsCalledItShouldReturnAListOfTracks() throws SQLException
	{
		var trackDTOs = new ArrayList<TrackDTO>();
		trackDTOs.add( new TrackDTO(
			1,
			"test",
			"performer",
			12,
			"album",
			null,
			null,
			null,
			false
		) );
		var tracksDTO = new TracksDTO(trackDTOs);

		Mockito.when( this.mockedService.getAllExcludingPlaylist( Mockito.anyLong() ) )
			.thenReturn(tracksDTO);

		var result = this.sut.getTracks("token", 2);

		assertEquals( tracksDTO, result.getEntity() );
	}
}
