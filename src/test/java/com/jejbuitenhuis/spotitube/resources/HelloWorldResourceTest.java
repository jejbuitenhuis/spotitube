package com.jejbuitenhuis.spotitube.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloWorldResourceTest
{
	private HelloWorldResource sut;

	@BeforeEach
	public void setup()
	{
		this.sut = new HelloWorldResource();
	}

	@Test
	public void whenHelloIsCalledItShouldReturnAnObjectContainingHelloWithValueWorld()
	{
		final var ex = "{ \"hello\": \"world!\" }";

		var ret = this.sut.hello();

		assertSame( ex, ret.getEntity() );
	}
}