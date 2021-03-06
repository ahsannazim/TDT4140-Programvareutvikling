package tdt4140.gr1878.app.ui;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

import tdt4140.gr1878.app.ui.db.Database;
import tdt4140.gr1878.app.ui.db.DatabaseImpl;

/**
 * Created by Polakken97 1. mar. 2018
 */

public class TestDatabaseConnection {

	@Test
	public void test() throws IOException {
		Thread.currentThread().setName("Test Database Connection");

		 
		 Database db = new DatabaseImpl("mysql.stud.ntnu.no", "maciejks_g78db", "maciejks_g78", "gruppe78");
		db.consume_query(
				"SELECT users_username AS username, users_password AS password, users_email AS email "
						+ "FROM maciejks_g78db.users",

				rs -> {
					try {
						if (rs.next()) {

						} else {

						}
					} catch (SQLException e1) {
						fail("Kan ikke koble til database, error");
					} catch (NullPointerException en) {
						fail("Kan ikke koble til database, error");
					}

				});
	}
}
