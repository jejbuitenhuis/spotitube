package com.jejbuitenhuis.spotitube.util.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class DAO<T>
{
	protected abstract T parse(ResultSet row) throws SQLException;
	protected abstract String getQueryAll();
	protected abstract String getQueryAllMatching();

	public List<T> getAll() throws SQLException
	{
		String queryString = this.getQueryAll();
		assert queryString != null && !queryString.isEmpty()
			: String.format("Query string for %s:getAll is null or empty",
				this.getClass().getSimpleName() );

		var query = Query.<T>create()
			.withQuery(queryString)
			.withParser(this::parse)
			.build();
		return query.execute();
	}

	public <N> List<T> getAllMatching(N needle) throws SQLException
	{
		assert needle != null : "needle cannot be null";

		String queryString = this.getQueryAllMatching();
		assert queryString != null && !queryString.isEmpty()
			: String.format("Query string for %s:getAllMatching is null or empty",
				this.getClass().getSimpleName() );

		var query = Query.<T>create()
			.withQuery(queryString)
			.withParser(this::parse)
			.withParameters( new Object[]
				{
					needle,
				})
			.build();
		return query.execute();
	}
}
