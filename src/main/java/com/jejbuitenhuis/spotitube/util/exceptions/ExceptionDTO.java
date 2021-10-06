package com.jejbuitenhuis.spotitube.util.exceptions;

public class ExceptionDTO
{
	public final boolean error = true;
	public final String type, message;

	public ExceptionDTO(String type, String message)
	{
		this.type = type;
		this.message = message;
	}
}
