package com.fc.commons.util;

import java.io.IOException;
import java.util.Properties;

public class Config {

	private static Properties p = null;
	private static String fileName = "config.properties";
	static {
		p = new Properties();
		try {
			p.load(Evn.class.getClassLoader().getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key){
		if(p != null){
			return p.getProperty(key);
		}
		
		return null;
	}
}
