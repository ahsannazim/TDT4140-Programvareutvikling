package tdt4140.gr1878.app.core;


import static org.junit.Assert.*;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import tdt4140.gr1878.app.core.Group;
import tdt4140.gr1878.app.core.User;

	
public class TestAddUser {
	
	public User leader1;
	public int groupGoal1;
	public String groupName1;
	//public List<User> members= new ArrayList<User>();
	public User user1;
	public User user2;
	
	
	@Test
	public void testAddUser() {
		Group test= new Group(groupName1, leader1, groupGoal1);
		test.addUser(user1);
		test.addUser(user2);
		assertEquals(2, test.getMembers().size());
		
	}

}
