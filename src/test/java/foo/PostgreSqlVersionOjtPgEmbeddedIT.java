package foo;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;

public class PostgreSqlVersionOjtPgEmbeddedIT {
	private static final int PORT = 35432;
	private static final String HOST = "127.0.0.1";

	private static EmbeddedPostgres pg;

	@BeforeClass
	public static void beforeClass() throws IOException, SQLException {

		EmbeddedPostgres.Builder pgBuilder = EmbeddedPostgres.builder().setPort(PORT);
		pg = pgBuilder.start();
		try (Connection cn = pg.getPostgresDatabase().getConnection()) {
			execute(cn, "CREATE DATABASE foodb;");
			execute(cn, "CREATE USER foouser WITH PASSWORD 'foopassword';");
			execute(cn, "GRANT ALL PRIVILEGES ON DATABASE foodb to foouser ;");
		}

//		try (Connection cn = pg.getDatabase("foouser", "foodb").getConnection()) {
//			execute(cn, "CREATE SCHEMA public");
//			execute(cn, "GRANT ALL ON SCHEMA public TO foouser;");
//		}
	}
	
	@AfterClass
	public static void afterClass() throws IOException {
		if (pg!=null) {
			pg.close();
		}
	}

	private static void execute(Connection cn, String sql) throws SQLException {
		try (CallableStatement callable = cn.prepareCall(sql)) {
			callable.execute();
		}
	}

	@Test
	public void retunsCorrectVersion() throws ClassNotFoundException, SQLException {
		Assert.assertTrue(PostgeSqlVersion.getVersion(HOST, PORT).contains("10.3"));
	}

	/*class ClasspathBinaryResolver implements PgBinaryResolver {
		public InputStream getPgBinary(String system, String machineHardware) throws IOException {
			ClassPathResource resource = new ClassPathResource(
					String.format("postgresql-%s-%s.txz", system, machineHardware));
			return resource.getInputStream();
		}
	}*/

}
