package org.sonatype.mavenbook.weather;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class YahooRetriever {
	
	private static Logger log = Logger.getLogger(YahooRetriever.class);
	
	public InputStream retrieve(int code) throws MalformedURLException, IOException {
		log.info("Retrieving weather data");
		String url = "ttp://weather.yahooapis.com/forecastrss?p="+code;
		URLConnection conn = new URL(url).openConnection();
		return conn.getInputStream();
	}
	
	
}
