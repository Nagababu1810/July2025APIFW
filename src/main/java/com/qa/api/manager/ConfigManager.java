package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	//read properitis from config.propetyfiles
	
	private static Properties prop =new Properties();
	
	
	static {
		
		String EnvName = System.getProperty("env", "qa");
		System.out.print("running testcase on env:"+EnvName);
		String filename= "Config_"+EnvName+".properties";
		
		
		//String filename ="Config.properties";
		
	InputStream input=	ConfigManager.class.getResourceAsStream(filename);
	
	if(input!=null) {
		try {
			prop.load(input);
			System.out.println("config properties+"+prop);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	}
	
	public static String get(String key) {
		
		return prop.getProperty(key);
	}
	
	public static void set(String key,String value) {
		prop.setProperty(key, value);
	}
	
	}

