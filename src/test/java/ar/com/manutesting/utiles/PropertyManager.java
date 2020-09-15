package ar.com.manutesting.utiles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
	
	private static PropertyManager instance;
	private static final Object lock = new Object();
	private static String propertyFilePath = "./src/test/resources/config.properties";
	private static String url;
	
	public static PropertyManager getIntance() throws FileNotFoundException, IOException{
		if(instance ==null) {
			synchronized (lock) {
				instance = new PropertyManager();
				instance.loadData();
			}
		}
		return instance;
	}

	private void loadData() throws FileNotFoundException, IOException {
		Properties propiedades = new Properties();
		propiedades.load(new FileInputStream(propertyFilePath));
		url = propiedades.getProperty("url");
	}
	
	public String getUrl() {
		return url;
	}

}
