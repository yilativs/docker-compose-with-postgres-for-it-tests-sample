package foo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class PostgeSqlVersion {

	public static String getVersion(String host, int port) throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://" + host + ":" + port + "/foodb?loginTimeout=10";
		Properties props = new Properties();
		props.setProperty("user", "foouser");
		props.setProperty("password", "foosecret");
//		props.setProperty("loginTimeout", "10");//in seconds
		try (Connection connection = DriverManager.getConnection(url, props)) {
			ResultSet resultSet = connection.createStatement().executeQuery("select * from version()");
			resultSet.next();
			return resultSet.getString(1);
		}
	}

}
