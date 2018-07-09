package foo;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

public class PostgreSqlVersionIT {
	@Test
	public void retunsCorrectVersion() throws ClassNotFoundException, SQLException {
		Assert.assertTrue(PostgeSqlVersion.getVersion().contains("9.6.9"));
	}

}
