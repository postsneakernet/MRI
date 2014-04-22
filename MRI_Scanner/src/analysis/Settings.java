package analysis;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

public class Settings {
	static Properties propDefault = new Properties();
	static Properties propUser = new Properties(propDefault);
	
	public static void getDefaultProperties() {
		try (FileInputStream in = new FileInputStream("propDefault")) {
			propDefault.load(in);
			propUser = new Properties(propDefault);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static void setDefaultProperties() {
		try (OutputStream out = new FileOutputStream("propDefault")) {
			propDefault.setProperty("initialDir", "user.dir");
			propDefault.setProperty("rememberDir", "true");
			propDefault.setProperty("ignoreAnalyzed", "true");
			propDefault.setProperty("lastPatient", "null");
			propDefault.store(out, null);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static void getProperty() {
		try (InputStream in = new FileInputStream("propUser")) {
			propUser.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Enumeration<?> e = propUser.propertyNames();
		
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = propUser.getProperty(key);
			System.out.println(key + ": " + value);
		}
	}
	
	public static String getProperty(String key) {
		try (InputStream in = new FileInputStream("propUser")) {
			propUser.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String value = propUser.getProperty(key);
		return value;
	}
	
	public static void setProperty(String key, String value) {	
		try	(OutputStream out = new FileOutputStream("propUser")) {
			propUser.setProperty(key, value);
			propUser.store(out, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}