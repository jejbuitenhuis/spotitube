package com.jejbuitenhuis.spotitube.util.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DAO<T>
{
	protected abstract T parse(ResultSet row) throws SQLException;
	protected abstract String getQueryAll();
	protected abstract String getQueryAllMatching();
	protected abstract String getQuerySave();
	protected abstract String getQueryDelete();
	protected abstract String getQueryUpdate();

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

	public int save(Object ...parameters) throws SQLException
	{
		String queryString = this.getQuerySave();
		assert queryString != null && !queryString.isEmpty()
			: String.format("Query string for %s:save is null or empty",
				this.getClass().getSimpleName() );

		var query = Query.create()
			.withQuery(queryString)
			.withParameters(parameters)
			.build();

		query.execute();

		return query.getInsertedId();
	}

	public <I> void delete(I ...ids) throws SQLException
	{
		String queryString = this.getQueryDelete();
		assert queryString != null && !queryString.isEmpty()
			: String.format("Query string for %s:update is null or empty",
				this.getClass().getSimpleName() );

		var query = Query.create()
			.withQuery(queryString)
			.withParameters(ids)
			.build();

		query.execute();
	}

	public <I> void update(I id, Object ...parameters) throws SQLException
	{
		String queryString = this.getQueryUpdate();
		assert queryString != null && !queryString.isEmpty()
			: String.format("Query string for %s:update is null or empty",
				this.getClass().getSimpleName() );

		var params = new ArrayList<>( List.of(parameters) );
		params.add(id);

		var query = Query.create()
			.withQuery(queryString)
			.withParameters( params.toArray() )
			.build();

		query.execute();
	}
}
