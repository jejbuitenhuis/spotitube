package com.jejbuitenhuis.spotitube.util.database;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DAOTest
{
	private DAO<String> getDAO(String returnValue)
	{
		return new DAO<>()
		{
			@Override
			protected String parse(ResultSet row) throws SQLException
			{
				return null;
			}

			@Override
			protected String getQueryAll()
			{
				return returnValue;
			}

			@Override
			protected String getQueryAllMatching()
			{
				return returnValue;
			}

			@Override
			protected String getQuerySave()
			{
				return returnValue;
			}
		};
	}

	@Test
	void whenGetAllIsCalledItShouldCreateAQueryAndCallExecuteOnIt() throws SQLException
	{
		var sut = this.getDAO("test");

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
		var sut = this.getDAO(null);

		assertThrows(AssertionError.class, sut::getAll);
	}

	@Test
	void whenGetAllMatchingIsCalledItShouldCreateAQueryAndCallExecuteOnIt() throws SQLException
	{
		var sut = this.getDAO("test");

		final var returnValue = new ArrayList<String>();
		returnValue.add("Test");

		var mockedQuery = Mockito.mock(Query.class);

		Mockito.when( mockedQuery.execute() ).thenReturn(returnValue);

		var mockedQueryBuilder = Mockito.mock(QueryBuilder.class);

		Mockito.when( mockedQueryBuilder.withQuery( Mockito.anyString() ) ).thenCallRealMethod();
		Mockito.when( mockedQueryBuilder.withParser( Mockito.any(QueryParser.class) ) ).thenCallRealMethod();
		Mockito.when( mockedQueryBuilder.withParameters( Mockito.any( Object[].class ) ) ).thenCallRealMethod();
		Mockito.when( mockedQueryBuilder.build() ).thenReturn(mockedQuery);

		try (var mock = Mockito.mockStatic(Query.class) )
		{
			mock.when(Query::create).thenReturn(mockedQueryBuilder);

			var result = sut.getAllMatching("test");

			mock.verify(Query::create);

			Mockito.verify(mockedQueryBuilder).withQuery( Mockito.anyString() );
			Mockito.verify(mockedQueryBuilder).withParser( Mockito.any(QueryParser.class) );
			Mockito.verify(mockedQueryBuilder).withParameters( Mockito.any( Object[].class ) );
			Mockito.verify(mockedQueryBuilder).build();

			Mockito.verify(mockedQuery).execute();

			assertEquals(returnValue, result);
		}
	}

	@Test
	void whenGetAllMatchingIsCalledAndGetQueryAllReturnsNullAnAssertionFailes()
	{
		var sut = this.getDAO(null);

		assertThrows( AssertionError.class, () -> sut.getAllMatching("test") );
	}
}
