package tdt4140.gr1878.app.ui;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import tdt4140.gr1878.app.ui.db.Database;
import tdt4140.gr1878.app.ui.db.DatabaseImpl;

public class DataCollectorTest {
	Database db;

	@Test
	public void test() {
		Thread.currentThread().setName("JavaFX");
		db = new DatabaseImpl("mysql.stud.ntnu.no", "maciejks_g78db", "maciejks_g78", "gruppe78");
		createDataCollector();
		String q="SELECT users_datacollector FROM maciejks_g78db.users WHERE users_email ='boss@gmail.com'";
		//System.out.println(q);
		db.consume_query(
				q,
				rs -> {
					try {
						if (rs.next() && (Integer.parseInt(rs.getString("users_datacollector")))==1) {
							System.out.println("User is DataCollector");
							db.query_update("DELETE FROM users WHERE users_email ='boss@gmail.com'");
						} else {
							fail("User is not DataCollector");
						}} catch (SQLException e1) {
							  fail("Kan ikke koble til database, error"); 
					} catch (NullPointerException en) {
						  fail("Kan ikke koble til database, error"); 
					}
				});
	}
	public void createDataCollector() {
		String q="INSERT INTO users (users_username, users_password, users_email, users_datacollector, privacy) VALUES"
				+ "('boss','123','boss@gmail.com', 1,0)";
		db.query_update(q);
		System.out.println("DataCollector created");
	}

}
