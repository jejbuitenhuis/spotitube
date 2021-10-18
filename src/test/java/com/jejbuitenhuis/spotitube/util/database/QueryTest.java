package com.jejbuitenhuis.spotitube.util.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
		var mockedKeys = Mockito.mock(ResultSet.class);

		Mockito.when( mockedKeys.next() )
				.thenReturn(false);

		var mockedPreparedStatement = Mockito.mock(PreparedStatement.class);

		Mockito.when( mockedPreparedStatement.execute() )
			.thenReturn(false);
		Mockito.when( mockedPreparedStatement.getGeneratedKeys() )
				.thenReturn(mockedKeys);

		var mockedConnection = Mockito.mock(Connection.class);

		Mockito.when( mockedConnection.prepareStatement( Mockito.anyString(), Mockito.eq(Statement.RETURN_GENERATED_KEYS) ) )
			.thenReturn(mockedPreparedStatement);

		try ( var mockedDriverManager = Mockito.mockStatic(DriverManager.class) )
		{
			mockedDriverManager.when( () -> DriverManager.getConnection( Mockito.anyString() ) )
				.thenReturn(mockedConnection);

			List<String> result = this.sut.execute();

			mockedDriverManager.verify( () -> DriverManager.getConnection( Mockito.anyString() ) );

			Mockito.verify(mockedConnection).prepareStatement( Mockito.anyString(), Mockito.eq(Statement.RETURN_GENERATED_KEYS) );
			Mockito.verify(mockedPreparedStatement).execute();
			Mockito.verify( mockedPreparedStatement, Mockito.never() ).getResultSet();
			Mockito.verify(mockedKeys).next();

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

		var mockedKeys = Mockito.mock(ResultSet.class);

		Mockito.when( mockedKeys.next() )
				.thenReturn(false);

		var mockedPreparedStatement = Mockito.mock(PreparedStatement.class);

		Mockito.when( mockedPreparedStatement.execute() )
			.thenReturn(true);
		Mockito.when( mockedPreparedStatement.getResultSet() )
			.thenReturn(mockedResultSet);
		Mockito.when( mockedPreparedStatement.getGeneratedKeys() )
			.thenReturn(mockedKeys);

		var mockedConnection = Mockito.mock(Connection.class);

		Mockito.when( mockedConnection.prepareStatement( Mockito.anyString(), Mockito.eq(Statement.RETURN_GENERATED_KEYS) ) )
			.thenReturn(mockedPreparedStatement);

		try ( var mockedDriverManager = Mockito.mockStatic(DriverManager.class) )
		{
			mockedDriverManager.when( () -> DriverManager.getConnection( Mockito.anyString() ) )
				.thenReturn(mockedConnection);

			List<String> result = this.sut.execute();

			mockedDriverManager.verify( () -> DriverManager.getConnection( Mockito.anyString() ) );

			Mockito.verify(mockedConnection).prepareStatement( Mockito.anyString(), Mockito.eq(Statement.RETURN_GENERATED_KEYS) );
			Mockito.verify(mockedPreparedStatement).execute();
			Mockito.verify(mockedPreparedStatement).getResultSet();
			Mockito.verify(mockedPreparedStatement).getGeneratedKeys();
			Mockito.verify( mockedResultSet, Mockito.times(2) )
				.next();
			Mockito.verify( mockedResultSet, Mockito.times(1) )
				.getString( Mockito.anyInt() );
			Mockito.verify(mockedKeys).next();

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

		Mockito.when( mockedConnection.prepareStatement( Mockito.anyString(), Mockito.eq(Statement.RETURN_GENERATED_KEYS) ) )
			.thenReturn(mockedPreparedStatement);

		try ( var mockedDriverManager = Mockito.mockStatic(DriverManager.class) )
		{
			mockedDriverManager.when( () -> DriverManager.getConnection( Mockito.anyString() ) )
				.thenReturn(mockedConnection);

			assertThrows(IllegalArgumentException.class, sut::execute);

			mockedDriverManager.verify( () -> DriverManager.getConnection( Mockito.anyString() ) );

			Mockito.verify(mockedConnection).prepareStatement( Mockito.anyString(), Mockito.eq(Statement.RETURN_GENERATED_KEYS) );
		}
	}
}
