package com.jejbuitenhuis.spotitube.resources;

import com.jejbuitenhuis.spotitube.util.database.Query;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Path("/")
public class HelloWorldResource
{
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response hello()
	{
		return Response.ok("{ \"hello\": \"world!\" }").build();
	}

	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public Response test() throws SQLException
	{
		var passwords = List.of(new String[]{
			"Test",
			"GoedWachtwoord!",
			"B3t3rW4chtw00rd!",
			"SlechtWachtwoord",
		});
		var hashedPasswords = passwords.stream()
				.map(DigestUtils::sha256Hex)
				.collect( Collectors.toList() );

		var res = Query.create()
				.withQuery("SELECT * FROM users")
				.withParser(row -> new Object()
				{
					public final String username = row.getString("username");
					public final String password = row.getString("password");
				})
				.build().execute();

		return Response.ok(res).build();
	}
}
