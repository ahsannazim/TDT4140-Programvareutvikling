package tdt4140.gr1878.app.core;


import static org.junit.Assert.*;

import org.junit.Test;

import tdt4140.gr1878.app.core.Group;
import tdt4140.gr1878.app.core.User;

public class TestPoints {
	private int currentStatus=0;
	private User leader;
	private int groupGoal;
	private String groupName;
	
	@Test
	public void test() {
		Group test= new Group(groupName, leader, groupGoal);
		test.addActivity(10);
		assertEquals(10,test.getPoints());
	}

}
