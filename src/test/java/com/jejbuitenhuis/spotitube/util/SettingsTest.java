package com.jejbuitenhuis.spotitube.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingsTest
{
	Settings sut;

	@BeforeEach
	void setup()
	{
		this.sut = new Settings();
	}

	@Test
	void whenGetIsCalledWithTestSuccessfulIsReturned()
	{
		var result = this.sut.get("test");

		assertEquals("successful", result);
	}
}
