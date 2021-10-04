package com.jejbuitenhuis.spotitube.util.database;

import javax.validation.constraints.NotNull;

public class QueryBuilder<T>
{
	private String query;
	private Object parameters[];
	private QueryParser<T> parser;

	public QueryBuilder<T>
	withQuery(@NotNull String query)
	{
		this.query = query;

		return this;
	}

	public QueryBuilder<T>
	withParameters(@NotNull Object parameters[])
	{
		this.parameters = parameters;

		return this;
	}

	public QueryBuilder<T>
	withParser(@NotNull QueryParser<T> parser)
	{
		this.parser = parser;

		return this;
	}

	public Query<T>
	build()
	{
		if ( this.query == null || this.query.isEmpty() )
			throw new RuntimeException("Query was not defined");

		if (this.parameters == null) this.parameters = new Object[0];

		return new Query<>(this.query, this.parameters, this.parser);
	}
}
