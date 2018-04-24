package tdt4140.gr1878.app.ui;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import tdt4140.gr1878.app.ui.db.Database;
import tdt4140.gr1878.app.ui.db.DatabaseImpl;

/**
 * Created by Polakken97
 * 5. mar. 2018
 */

public class EditEmailTest {
	Database db;

	@Test
	public void test() throws IOException {
		Thread.currentThread().setName("Test Database Connection");

		 
		  db = new DatabaseImpl("mysql.stud.ntnu.no", "maciejks_g78db", "maciejks_g78", "gruppe78");
		 
		 			createTestUser();
						db.consume_query(
								"SELECT users_username AS username, users_email AS email "
										+ "FROM maciejks_g78db.users WHERE users_username='" + "Marius" + "';",
										rs -> {
											try {
												if (rs.next()) {
												
														
														db.query_update(
																"UPDATE users "
																+ "SET users_email = '" + "lol1@gmail.com" + "' WHERE users_username = '" + "Marius" + "';");
																		
													
													
												} else {
													fail("Failed updating email");
													}
												} catch (SQLException e1) {
													fail("SQL fail");
												e1.printStackTrace();
											} catch (NullPointerException en) {
												fail("SQL fail");
												en.printStackTrace();
											}
										
										});
	}
	

	public void createTestUser() {
		db.query_update(
				"INSERT INTO users (users_username, users_password, users_email, users_birthday, users_weight, users_height, users_name ) VALUES ('Marius','Pass', +"
				+ "'lol@gmail.com', + '32', + '32', '32','mac'  );");
	}
}
