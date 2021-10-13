package com.jejbuitenhuis.spotitube.authentication.usersession;

import com.jejbuitenhuis.spotitube.util.database.Query;
import com.jejbuitenhuis.spotitube.util.database.QueryBuilder;
import com.jejbuitenhuis.spotitube.util.database.QueryParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserSessionDAOTest
{
	UserSessionDAO sut;

	@BeforeEach
	void setup()
	{
		this.sut = new UserSessionDAO();
	}

	@Test
	void whenParseIsCalledItShouldReturnANewUserSessionWithTheCorrectValues() throws SQLException
	{
		final var expectedUsername = "test1";
		final var expectedToken = "58feed5a-3656-4351-8427-4122c48da2f9";
		final var expected = new UserSession(expectedUsername, expectedToken);

		var mockedResultSet = Mockito.mock(ResultSet.class);

		Mockito.when( mockedResultSet.getString("user") )
			.thenReturn(expectedUsername);
		Mockito.when( mockedResultSet.getString("token") )
			.thenReturn(expectedToken);

		var result = this.sut.parse(mockedResultSet);

		Mockito.verify(mockedResultSet).getString("user");
		Mockito.verify(mockedResultSet).getString("token");

		assertEquals( expected.getUser(), result.getUser() );
		assertEquals( expected.getToken(), result.getToken() );
	}

	@Test
	void whenGetQueryAllIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "SELECT * FROM user_sessions;";

		var result = this.sut.getQueryAll();

		assertEquals(expected, result);
	}

	@Test
	void whenGetQueryAllMatchingIsCalledItShouldReturnTheCorrectQuery()
	{
		final String expected = "SELECT * FROM user_sessions WHERE token = ?;";

		var result = this.sut.getQueryAllMatching();

		assertEquals(expected, result);
	}

	@Test
	void whenSaveIsCalledItShouldSaveAUserSessionUsingAQuery() throws SQLException
	{
		final var session = new UserSession(
			"test1",
			"58feed5a-3656-4351-8427-4122c48da2f9"
		);

		var mockedQuery = Mockito.mock(Query.class);

		Mockito.when( mockedQuery.execute() ).thenReturn(null);

		var mockedQueryBuilder = Mockito.mock(QueryBuilder.class);

		Mockito.when( mockedQueryBuilder.withQuery( Mockito.anyString() ) )
			.thenCallRealMethod();
		Mockito.when( mockedQueryBuilder.withParameters( Mockito.any(Object[].class) ) )
			.thenCallRealMethod();
		Mockito.when( mockedQueryBuilder.build() ).thenReturn(mockedQuery);

		try ( var mock = Mockito.mockStatic(Query.class) )
		{
			mock.when(Query::create).thenReturn(mockedQueryBuilder);

			this.sut.save( session.getUser(), session.getToken().toString() );

			mock.verify(Query::create);

			Mockito.verify(mockedQueryBuilder).withQuery( Mockito.anyString() );
			Mockito.verify(mockedQueryBuilder).withParameters( Mockito.any(Object[].class) );
			Mockito.verify(mockedQueryBuilder).build();
			Mockito.verify( mockedQueryBuilder, Mockito.never() )
				.withParser( Mockito.any(QueryParser.class) );

			Mockito.verify(mockedQuery).execute();
		}
	}
}
