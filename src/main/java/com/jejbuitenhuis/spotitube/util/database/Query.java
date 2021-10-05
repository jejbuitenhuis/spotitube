package com.jejbuitenhuis.spotitube.util.database;

import com.jejbuitenhuis.spotitube.util.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Query<T>
{
	private final Logger logger = Logger.getLogger( this.getClass().getName() );

	private final String query;
	private final Object parameters[];
	private final QueryParser<T> parser;

	private Connection connection;

	Query(String query, Object parameters[], QueryParser<T> parser)
	{
		this.query = query;
		this.parameters = parameters;
		this.parser = parser;
	}

	public static <T> QueryBuilder<T> create()
	{
		return new QueryBuilder<>();
	}

	public List<T> execute() throws SQLException
	{
		final var settings = Settings.getInstance();
		ArrayList<T> ret = null;

		try
		{
			Class.forName( settings.get("database.driver") );

			this.connection = DriverManager.getConnection(
				settings.get("database.url")
			);
			var statement = this.createPreparedStatement();

			assert statement != null;

			boolean hasResult = statement.execute();

			if (hasResult)
			{
				assert this.parser != null;

				ret = new ArrayList<>();
				var result = statement.getResultSet();

				while ( result.next() )
					ret.add( this.parser.parse(result) );

				result.close();
			}

			this.connection.close();
			this.connection = null;
		}
		catch (ClassNotFoundException e)
		{
			this.logger.log(
				Level.SEVERE,
				"Error communicating with database",
				e
			);
		}

		return ret;
	}

	private PreparedStatement createPreparedStatement() throws SQLException
	{
		if (this.connection == null) return null;

		var statement = this.connection.prepareStatement(this.query);

		for (int i = 0; i < this.parameters.length; i++)
		{
			var param = this.parameters[i];

			if (param instanceof Integer)
				statement.setInt( i + 1, (Integer) param );
			else if (param instanceof String)
				statement.setString( i + 1, (String) param );
			else if (param instanceof Boolean)
				statement.setBoolean( i + 1, (Boolean) param );
			else
				throw new IllegalArgumentException(
					"Type of query parameters should be one of \"Integer\"" +
						", \"String\" or \"Boolean\", not \""
						+ param.getClass().getSimpleName() + "\""
				);
		}

		return statement;
	}
}
