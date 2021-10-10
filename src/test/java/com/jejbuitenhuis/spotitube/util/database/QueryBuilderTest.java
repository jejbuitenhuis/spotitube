package com.jejbuitenhuis.spotitube.util.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryBuilderTest
{
	@Test
	void whenBuildIsCalledWithoutAQueryItShouldThrowARuntimeException()
	{
		final Object parameters[] = new Object[]{ "test1" };
		final QueryParser<String> parser = row -> "test";

		var sut = new QueryBuilder<String>()
			.withParameters(parameters)
			.withParser(parser);

		var exception = assertThrows(RuntimeException.class, sut::build);

		assertEquals( "Query was not defined", exception.getMessage() );
	}
}
