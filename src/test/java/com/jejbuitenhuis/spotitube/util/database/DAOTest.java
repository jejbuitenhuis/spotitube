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

			@Override
			protected String getQueryDelete()
			{
				return returnValue;
			}

			@Override
			protected String getQueryUpdate()
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

	@Test
	void whenSaveIsCalledItShouldSaveTheGivenObjects() throws SQLException
	{
		var sut = this.getDAO("test");

		var arguments = new Object[]{ "test", "whatever", "something" };

		var mockedQuery = Mockito.mock(Query.class);

		Mockito.when( mockedQuery.execute() ).thenReturn(null);

		var mockedQueryBuilder = Mockito.mock(QueryBuilder.class);

		Mockito.when( mockedQueryBuilder.withQuery( Mockito.anyString() ) ).thenCallRealMethod();
		Mockito.when( mockedQueryBuilder.withParameters( Mockito.any( Object[].class ) ) ).thenCallRealMethod();
		Mockito.when( mockedQueryBuilder.build() ).thenReturn(mockedQuery);

		try (var mock = Mockito.mockStatic(Query.class) )
		{
			mock.when(Query::create).thenReturn(mockedQueryBuilder);

			sut.save(arguments);

			mock.verify(Query::create);

			Mockito.verify(mockedQueryBuilder).withQuery( Mockito.anyString() );
			Mockito.verify(mockedQueryBuilder).withParameters(arguments);
			Mockito.verify(mockedQueryBuilder).build();

			Mockito.verify(mockedQuery).execute();
		}
	}

	@Test
	void whenSaveIsCalledAndGetQuerySaveReturnsNullAnAssertionFails()
	{
		var sut = this.getDAO(null);

		assertThrows( AssertionError.class, () -> sut.save("test") );
	}

	@Test
	void whenDeleteIsCalledItShouldDeleteTheGivenObject() throws SQLException
	{
		var sut = this.getDAO("test");

		Long arguments[] = new Long[]{ 2L };

		var mockedQuery = Mockito.mock(Query.class);

		Mockito.when( mockedQuery.execute() ).thenReturn(null);

		var mockedQueryBuilder = Mockito.mock(QueryBuilder.class);

		Mockito.when( mockedQueryBuilder.withQuery( Mockito.anyString() ) ).thenCallRealMethod();
		Mockito.when( mockedQueryBuilder.withParameters( Mockito.any( Object[].class ) ) ).thenCallRealMethod();
		Mockito.when( mockedQueryBuilder.build() ).thenReturn(mockedQuery);

		try (var mock = Mockito.mockStatic(Query.class) )
		{
			mock.when(Query::create).thenReturn(mockedQueryBuilder);

			sut.delete(arguments);

			mock.verify(Query::create);

			Mockito.verify(mockedQueryBuilder).withQuery( Mockito.anyString() );
			Mockito.verify(mockedQueryBuilder).withParameters(arguments);
			Mockito.verify(mockedQueryBuilder).build();

			Mockito.verify(mockedQuery).execute();
		}
	}

	@Test
	void whenDeleteIsCalledAndGetQueryDeleteReturnsNullAnAssertionFails()
	{
		var sut = this.getDAO(null);

		assertThrows( AssertionError.class, () -> sut.delete("test") );
	}

	@Test
	void whenUpdateIsCalledItShouldUpdateTheGivenObject() throws SQLException
	{
		var sut = this.getDAO("test");

		long id = 2L;
		var arguments = new Object[]{ "test" };
		var expectedArguments = new Object[]{ arguments[0], id };

		var mockedQuery = Mockito.mock(Query.class);

		Mockito.when( mockedQuery.execute() ).thenReturn(null);

		var mockedQueryBuilder = Mockito.mock(QueryBuilder.class);

		Mockito.when( mockedQueryBuilder.withQuery( Mockito.anyString() ) ).thenCallRealMethod();
		Mockito.when( mockedQueryBuilder.withParameters( Mockito.any( Object[].class ) ) ).thenCallRealMethod();
		Mockito.when( mockedQueryBuilder.build() ).thenReturn(mockedQuery);

		try (var mock = Mockito.mockStatic(Query.class) )
		{
			mock.when(Query::create).thenReturn(mockedQueryBuilder);

			sut.update(id, arguments);

			mock.verify(Query::create);

			Mockito.verify(mockedQueryBuilder).withQuery( Mockito.anyString() );
			Mockito.verify(mockedQueryBuilder).withParameters(expectedArguments);
			Mockito.verify(mockedQueryBuilder).build();

			Mockito.verify(mockedQuery).execute();
		}
	}

	@Test
	void whenUpdateIsCalledAndGetQueryUpdateReturnsNullAnAssertionFails()
	{
		var sut = this.getDAO(null);

		assertThrows( AssertionError.class, () -> sut.update(1L, "test") );
	}
}
