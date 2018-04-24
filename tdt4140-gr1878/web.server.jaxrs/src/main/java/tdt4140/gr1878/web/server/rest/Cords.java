package tdt4140.gr1878.web.server.rest;

public class Cords {

	int cordX;
	String ownerName;
	public int getCordX() {
		return cordX;
	}

	public void setCordX(int cordX) {
		this.cordX = cordX;
	}

	public int getCordY() {
		return cordY;
	}

	public void setCordY(int cordY) {
		this.cordY = cordY;
	}

	int cordY;
	

	@Override
	public String toString() {
		return "Person: " + ownerName + "[At:=" + cordX + "," + cordY + "]";
	}

}