package tdt4140.gr1878.app.ui;

/**
 * Created by Polakken97
 * 2. mar. 2018
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

import tdt4140.gr1878.app.ui.db.Database;
import tdt4140.gr1878.app.ui.db.DatabaseImpl;

/**
 * Created by Polakken97
 * 1. mar. 2018
 */

public class TestDBMakeUser {

	@Test
	
	public void test() throws IOException {
		Thread.currentThread().setName("JavaFX");
		 Database db = new DatabaseImpl("mysql.stud.ntnu.no", "maciejks_g78db", "maciejks_g78", "gruppe78");
		db.consume_query(
				"SELECT users_username AS username, users_password AS password, users_email AS email "
						+ "FROM maciejks_g78db.users", 	
						
						rs -> {
							try {
								if (rs.next()) {
									db.query_update(
											"DELETE FROM users WHERE users_username='Marius'");
									
								} else {
								fail("Failed");
								}} catch (SQLException e1) {
									  fail("Kan ikke koble til database, error"); 
							} catch (NullPointerException en) {
								  fail("Kan ikke koble til database, error"); 
							}
							
						});
	}
}
