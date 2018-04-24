package tdt4140.gr1878.app.core;

import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class User {
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public String setEmail(String email) {
		if (checkValidEmail(email)=="") {
			this.email=email;
			return "";
		} else {
			String res = checkValidEmail(email);
			return res;
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPoints() {
		return points;
	}
	
	public void setdataCollector(int dataCollector) {
		this.dataCollector = dataCollector;
	}
	public int getdataCollector() {
		return dataCollector;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}


	public String name; //Fornavn og etternavn
	public String email; 
	public String password; //dette skal bare brukeren få se, hvordan gjør vi dette?
	public String username;
	public int points;
	public int birthday;
	public double weight;
	public double height;
	public Group group;
	public int id;
	public int dataCollector;
	
	
	/*
	public User(int id, String name, String username, String email, String password, int birthday, double weight, double height) {

	}
	private String name; //Fornavn og etternavn
	private String email; 
	private String password; //dette skal bare brukeren få se, hvordan gjør vi dette?
	private int points;
	private Group group;
	private int id;
	private String namefull;
	private int birthday;
	private int weight;
	private int height;
	*/
	
	
	
	public User(int id, String name, String username, String email, String password, int birthday, int weight, int height, int dataCollector) {
		this.id=id;
		this.name=name;
		this.email=email;
		this.password=password;

		this.birthday = birthday;
		this.weight = weight;
		this.height = height;
		this.username = username;
		
		this.birthday=birthday;
		this.weight=weight;
		this.height=height;
		this.dataCollector = dataCollector;
	}
	
	// Valideringsmetoder:
	
	public String checkValidEmail(String email) {
		if (!email.contains("@")) {
		return "Your email must contain: '@'";	
		} 
		String [] parts = email.split("@");
		String[] domainParts = parts[1].split("\\.");
	
		if (domainParts.length != 2 && domainParts.length !=3){
			return "The domain name in your email must contain: '.'";
		}
		return "";		
		}
	
	
	
	public void checkValidName(String name) {
		
	}
	
	public void savePassword(String password) {
		
	}
	
	// Poeng-behandling:
	public void addPoints(int points) {
		
	}
	



	//Endring av brukerinfo:
	public void changeName(String name) {
		
	}

	public void changeEmail(String email) {
		
	}
	
	public void changePassword(String newPassword) {
	
	}
	
	// Gruppemetoder

	
	public void addMyPointsToGroup(Group group, int newpoints) {
		
	}

}
