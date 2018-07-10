package foo;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

public class PostgreSqlVersionUsingDockerContainerIT {

	@Test
	public void retunsCorrectVersion() throws ClassNotFoundException, SQLException {
		Assert.assertTrue(PostgeSqlVersion.getVersion("127.0.0.1", 15432).contains("9.6.9"));
	}

}
