package tdt4140.gr1878.app.core;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

public class Group {
	private List<User> members=new ArrayList<User>();
	private User leader;
	private int groupGoal;
	private int currentStatus;
	private String groupName;
	
	public Group(String groupName, User leader, int groupGoal) {
		this.leader = leader;
		this.groupName = groupName;
		
		
	}
	public void validateUser(User user) {
		
	}
	public void addUser(User user) {
		validateUser(user);
		members.add(user);
	}
	
	
	public void removeUser(User user) {
		if( members.contains(user)){
			members.remove(user);
		}else { 
			throw new IllegalArgumentException ("user not found");
		}
		
	}
	public void addActivity(int points) {
		this.currentStatus += points;
	}
	public List<User> getMembers() {
		// TODO Auto-generated method stub
		return members;
	}
	public int getPoints() {
		// TODO Auto-generated method stub
		return currentStatus;
	}
	
	
	
	
	

}
