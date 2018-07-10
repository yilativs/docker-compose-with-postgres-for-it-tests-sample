package foo;

import static java.util.Collections.emptyList;
import static ru.yandex.qatools.embed.postgresql.EmbeddedPostgres.cachedRuntimeConfig;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.io.file.Files;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;

public class PostgreSqlVersionYandexEmbededPostgresIT {
	private static final int PORT = 25432;
	private static final String HOST = "127.0.0.1";
	private static EmbeddedPostgres POSTGRES;

	@BeforeClass
	public static void beforeClassStartPostgres() throws IOException, SQLException {
		String userHomDir = System.getProperty("user.home");
		Path dataDirPath = Paths.get(userHomDir, ".EmbeddedPostgres", "data");
		Files.createOrCheckDir(dataDirPath.toFile());
//		POSTGRES = new EmbeddedPostgres(() -> "9.6.2-1", dataDirPath.toString());
		POSTGRES = new EmbeddedPostgres(dataDirPath.toString());
		Path cacheDirPath = Paths.get(userHomDir, ".EmbeddedPostgres", "cache");
		Files.createOrCheckDir(cacheDirPath.toFile());
		IRuntimeConfig config = cachedRuntimeConfig(cacheDirPath);
		POSTGRES.start(config, HOST, PORT, "foodb", "foouser", "foosecret", emptyList());
	}

	@AfterClass
	public static void afterClassStopPostgres() throws IOException {
		if (POSTGRES != null) {
			POSTGRES.stop();
		}
	}

	@Test
	public void retunsCorrectVersion() throws ClassNotFoundException, SQLException {
		Assert.assertTrue(PostgeSqlVersion.getVersion(HOST, PORT).contains("10.3"));
	}

}
