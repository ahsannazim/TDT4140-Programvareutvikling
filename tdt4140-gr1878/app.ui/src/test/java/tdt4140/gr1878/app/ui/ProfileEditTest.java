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

public class ProfileEditTest {
	Database db;

	@Test
	public void test() throws IOException {
		Thread.currentThread().setName("Test Profile Edit");

		 
		  db = new DatabaseImpl("mysql.stud.ntnu.no", "maciejks_g78db", "maciejks_g78", "gruppe78");
		 
		 			createTestUser();
		 			db.consume_query(
							"SELECT users_username AS username, users_weight AS weight, users_height AS height "
									+ "FROM maciejks_g78db.users WHERE users_username='" + "Marius" + "';",
							rs -> {
								try {
									if (rs.next()) {
											db.query_update(
													"UPDATE users "
													+ "SET users_weight = '" + "3" +"', users_height = '" + "4" +"'"
															+ " WHERE users_username = '" + "Marius" + "';");
															
											
										
									} else {
										fail("Failed updating");
										}
									} catch (SQLException e1) {
										fail("SQL fail");
								} catch (NullPointerException en) {
									fail("SQL fail");
								}
								
							});
		 			db.consume_query(
							"SELECT users_username AS username, users_weight AS weight, users_height AS height "
									+ "FROM maciejks_g78db.users WHERE users_username='" + "Marius" + "';",
							rs -> {
								try {
									if (rs.next()) {
										int weight = Integer.parseInt(rs.getString("weight"));
										int height = Integer.parseInt(rs.getString("height"));
										if(! (weight==3 && height == 4)) {
											fail("Failed updating weight/height");
										}
									}else {
										fail("Failed updating weight/height");
									}
								} catch (SQLException e1) {
									fail("SQL fail");
							} catch (NullPointerException en) {
								fail("SQL fail");
							}
							});
		 			
						
	}
	

	public void createTestUser() {
		db.query_update(
				"INSERT INTO users (users_username, users_password, users_email, users_birthday, users_weight, users_height, users_name,users_datacollector,privacy ) VALUES ('Marius','Pass', +"
				+ "'lol@gmail.com', + '32', + '32', '32','mac',1,0  );");
	}
}
