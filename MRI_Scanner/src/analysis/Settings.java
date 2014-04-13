package analysis;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Settings {
	static Properties defaultProps = new Properties();
	static Properties appProps = new Properties(defaultProps);
	
	public static void getDefaultProperties() {
		try (FileInputStream in = new FileInputStream("defaultProperties")) {
			defaultProps.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static void getProperties() {
		try (InputStream in = new FileInputStream("appProperties")) {
			appProps.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(appProps.getProperty("mriImages"));
		System.out.println(appProps.getProperty("testProp"));
	}
	
	public static void setProperties() {		
		try	(OutputStream out = new FileOutputStream("appProperties")) {
			appProps.setProperty("mriImages", "user.dir");
			appProps.store(out, "--No Comment--");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
////		getDefaultProperties();
////		setProperties();
//		getProperties();
//	}
}