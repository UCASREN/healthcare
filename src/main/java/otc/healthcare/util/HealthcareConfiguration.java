package otc.healthcare.util;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;
import org.springframework.stereotype.Component;

@Component
public class HealthcareConfiguration {
	private static final String PROERTIES_FILE_NAME = "healthcare.properties";

	public static final String DB_URL = "db_url";
	public static final String DB_USERNAME = "db_username";
	public static final String DB_PASSWORD = "db_password";
	
	private Properties properties;

	public HealthcareConfiguration() {
		ClassLoader loader = HealthcareConfiguration.class.getClassLoader();
		URL url;
		try {
			url = loader.getResource(HealthcareConfiguration.PROERTIES_FILE_NAME);
			this.properties = new Properties();
			FileInputStream inputFile = new FileInputStream(
					java.net.URLDecoder.decode(url.getFile(), "utf-8"));

			this.properties.load(inputFile);
			inputFile.close();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}
}
