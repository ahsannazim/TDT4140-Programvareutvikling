package tdt4140.gr1878.app.ui;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import tdt4140.gr1878.app.ui.db.Database;
import tdt4140.gr1878.app.ui.db.DatabaseImpl;

public class RegisterGroupTest {
	Database db;
	
	
	@Test
	public void test() throws IOException{
		Thread.currentThread().setName("JavaFX");
		db = new DatabaseImpl("mysql.stud.ntnu.no", "maciejks_g78db", "maciejks_g78", "gruppe78");
		createTestGroup();
		String q="SELECT group_id AS id FROM maciejks_g78db.groups WHERE group_id =999";
		System.out.println(q);
		db.consume_query(
				"SELECT group_id AS id FROM maciejks_g78db.groups WHERE group_id = 999",
				rs -> {
					try {
						if (rs.next()) {
							db.query_update("DELETE FROM groups WHERE group_id=999");
							
						} else {
							fail("Could not find group");
						}} catch (SQLException e1) {
							  fail("Kan ikke koble til database, error"); 
					} catch (NullPointerException en) {
						  fail("Kan ikke koble til database, error"); 
					}
					
				});
		
	}
	public void createTestGroup() {
		db.query_update(
				"INSERT INTO groups (group_id, group_name, points_earned, points_goal,prize) VALUES (999,'stest', 30, 100,'boller')");
		
	}

}
