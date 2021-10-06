package com.jejbuitenhuis.spotitube.util.exceptions;

import java.util.List;

public class StacktraceExceptionDTO extends ExceptionDTO
{
	public final List<String> stacktrace;

	public StacktraceExceptionDTO(String type, String message, List<String> stacktrace)
	{
		super(type, message);

		this.stacktrace = stacktrace;
	}
}
