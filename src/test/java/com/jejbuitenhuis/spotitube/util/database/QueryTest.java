package com.jejbuitenhuis.spotitube.util.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class QueryTest
{
	Query<String> sut;

	@BeforeEach
	void setup()
	{
		this.sut = new Query<>( "test", new Object[]{ "test" }, row -> row.getString(0) );
	}

	@Test
	void whenExecuteIsCalledItShouldReturnNull() throws SQLException
	{
		var mockedPreparedStatement = Mockito.mock(PreparedStatement.class);

		Mockito.when( mockedPreparedStatement.execute() )
			.thenReturn(false);

		var mockedConnection = Mockito.mock(Connection.class);

		Mockito.when( mockedConnection.prepareStatement( Mockito.anyString() ) )
			.thenReturn(mockedPreparedStatement);

		try ( var mockedDriverManager = Mockito.mockStatic(DriverManager.class) )
		{
			mockedDriverManager.when( () -> DriverManager.getConnection( Mockito.anyString() ) )
				.thenReturn(mockedConnection);

			List<String> result = this.sut.execute();

			mockedDriverManager.verify( () -> DriverManager.getConnection( Mockito.anyString() ) );

			Mockito.verify(mockedConnection).prepareStatement( Mockito.anyString() );
			Mockito.verify(mockedPreparedStatement).execute();
			Mockito.verify( mockedPreparedStatement, Mockito.never() ).getResultSet();

			assertNull(result);
		}
	}

	@Test
	void whenExecuteIsCalledItShouldReturnAnArrayListWithOneElement() throws SQLException
	{
		final var expectedValue = "test";
		final var expected = new ArrayList<String>();
		expected.add(expectedValue);

		var mockedResultSet = Mockito.mock(ResultSet.class, Mockito.CALLS_REAL_METHODS);

		Mockito.when( mockedResultSet.next() )
			.thenReturn(true)
			.thenReturn(false);
		Mockito.when( mockedResultSet.getString( Mockito.anyInt() ) )
			.thenReturn(expectedValue);

		var mockedPreparedStatement = Mockito.mock(PreparedStatement.class);

		Mockito.when( mockedPreparedStatement.execute() )
			.thenReturn(true);
		Mockito.when( mockedPreparedStatement.getResultSet() )
			.thenReturn(mockedResultSet);

		var mockedConnection = Mockito.mock(Connection.class);

		Mockito.when( mockedConnection.prepareStatement( Mockito.anyString() ) )
			.thenReturn(mockedPreparedStatement);

		try ( var mockedDriverManager = Mockito.mockStatic(DriverManager.class) )
		{
			mockedDriverManager.when( () -> DriverManager.getConnection( Mockito.anyString() ) )
				.thenReturn(mockedConnection);

			List<String> result = this.sut.execute();

			mockedDriverManager.verify( () -> DriverManager.getConnection( Mockito.anyString() ) );

			Mockito.verify(mockedConnection).prepareStatement( Mockito.anyString() );
			Mockito.verify(mockedPreparedStatement).execute();
			Mockito.verify(mockedPreparedStatement).getResultSet();
			Mockito.verify( mockedResultSet, Mockito.times(2) )
				.next();
			Mockito.verify( mockedResultSet, Mockito.times(1) )
				.getString( Mockito.anyInt() );

			assertEquals(expected, result);
			assertEquals( expectedValue, result.get(0) );
		}
	}

	@Test
	void whenExecuteIsCalledWithAnIllegalParameterItShouldThrowAnException() throws SQLException
	{
		final var parameters = new Object[]{ new Object() };
		var sut = new Query<String>("test", parameters, row -> "test");

		var mockedPreparedStatement = Mockito.mock(PreparedStatement.class);

		var mockedConnection = Mockito.mock(Connection.class);

		Mockito.when( mockedConnection.prepareStatement( Mockito.anyString() ) )
			.thenReturn(mockedPreparedStatement);

		try ( var mockedDriverManager = Mockito.mockStatic(DriverManager.class) )
		{
			mockedDriverManager.when( () -> DriverManager.getConnection( Mockito.anyString() ) )
				.thenReturn(mockedConnection);

			assertThrows(IllegalArgumentException.class, sut::execute);

			mockedDriverManager.verify( () -> DriverManager.getConnection( Mockito.anyString() ) );

			Mockito.verify(mockedConnection).prepareStatement( Mockito.anyString() );
		}
	}
}
