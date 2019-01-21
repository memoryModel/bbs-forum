package com.fc.commons.util;

import java.io.IOException;
import java.util.Properties;

public class Evn {

	private static Properties p = null;
	private static String fileName = "oneportal.properties";
	static {
		p = new Properties();
		try {
			p.load(Evn.class.getClassLoader().getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getEvn(){
		if(p != null){
			return p.getProperty("evn");
		}
		
		return null;
	}
	
	public static String getProperty(String key){
		if(p != null){
			return p.getProperty(key);
		}
		
		return null;
	}
	
}
