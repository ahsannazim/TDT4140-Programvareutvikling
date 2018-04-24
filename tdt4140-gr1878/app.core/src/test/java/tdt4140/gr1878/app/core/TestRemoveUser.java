package tdt4140.gr1878.app.core;


import static org.junit.Assert.*;

import org.junit.Test;

import tdt4140.gr1878.app.core.Group;
import tdt4140.gr1878.app.core.User;

public class TestRemoveUser {
	private User leader;
	private int groupGoal;
	private String groupName;
	public User user1;
	public User user2;
	
	@Test
	public void testRemoveUser() {
		Group test= new Group(groupName, leader, groupGoal);
		test.addUser(user1);
		test.addUser(user2);
		test.removeUser(user1);
		assertEquals(1,test.getMembers().size());
		
	}

}
