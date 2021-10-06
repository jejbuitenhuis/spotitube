package com.jejbuitenhuis.spotitube.util.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.opentest4j.AssertionFailedError;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DAOTest
{
	@Test
	void whenGetAllIsCalledItShouldCreateAQueryAndCallExecuteOnIt() throws SQLException
	{
		var sut = new DAO<>()
		{
			@Override
			protected String parse(ResultSet row) throws SQLException
			{
				return null;
			}

			@Override
			protected String getQueryAll()
			{
				return "test";
			}

			@Override
			protected String getQueryAllMatching()
			{
				return "test";
			}
		};

		final var returnValue = new ArrayList<String>();
		returnValue.add("Test");

		var mockedQuery = Mockito.mock(Query.class);

		Mockito.when( mockedQuery.execute() ).thenReturn(returnValue);

		var mockedQueryBuilder = Mockito.mock(QueryBuilder.class);

		Mockito.when( mockedQueryBuilder.withQuery( Mockito.anyString() ) ).thenCallRealMethod();
		Mockito.when( mockedQueryBuilder.withParser( Mockito.any(QueryParser.class) ) ).thenCallRealMethod();
		Mockito.when( mockedQueryBuilder.build() ).thenReturn(mockedQuery);

		try (var mock = Mockito.mockStatic(Query.class) )
		{
			mock.when(Query::create).thenReturn(mockedQueryBuilder);

			var result = sut.getAll();

			mock.verify(Query::create);

			Mockito.verify(mockedQueryBuilder).withQuery( Mockito.anyString() );
			Mockito.verify(mockedQueryBuilder).withParser( Mockito.any(QueryParser.class) );
			Mockito.verify(mockedQueryBuilder).build();

			Mockito.verify(mockedQuery).execute();

			assertEquals(returnValue, result);
		}
	}

	@Test
	void whenGetAllIsCalledAndGetQueryAllReturnsNullAnAssertionFailes()
	{
		var sut = new DAO<>()
		{
			@Override
			protected String parse(ResultSet row) throws SQLException
			{
				return null;
			}

			@Override
			protected String getQueryAll()
			{
				return null;
			}

			@Override
			protected String getQueryAllMatching()
			{
				return null;
			}
		};

		assertThrows(AssertionError.class, sut::getAll);
	}
}
