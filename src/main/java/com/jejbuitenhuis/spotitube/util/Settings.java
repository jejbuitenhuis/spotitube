package com.jejbuitenhuis.spotitube.util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings
{
	private static final Settings INSTANCE = new Settings();

	private final Logger logger = Logger.getLogger( this.getClass().getName() );
	private final Properties properties;

	public Settings()
	{
		this.properties = new Properties();

		try
		{
			this.properties.load(
				this.getClass().getClassLoader()
						.getResourceAsStream("settings.properties")
			);
		}
		catch (IOException e)
		{
			this.logger.log(
				Level.SEVERE,
				"Can't access property file \"settings.properties\"",
				e
			);
		}
	}

	public static Settings getInstance()
	{
		return INSTANCE;
	}

	public String get(String key)
	{
		return this.properties.getProperty(key);
	}
}
