package com.jejbuitenhuis.spotitube.util.database;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface QueryParser<T>
{
	T parse(ResultSet row) throws SQLException;
}
