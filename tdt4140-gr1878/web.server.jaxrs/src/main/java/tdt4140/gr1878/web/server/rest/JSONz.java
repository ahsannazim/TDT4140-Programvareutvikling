package tdt4140.gr1878.web.server.rest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//Depricated
public class JSONz{
	
	@SuppressWarnings("deprecation")
	public static String readJson() throws FileNotFoundException, IOException, ParseException {
JSONParser parser = new JSONParser();
//Use JSONObject for simple JSON and JSONArray for array of JSON.
JSONObject data = (JSONObject) parser.parse(
      new FileReader("geoLocations.json"));//path to the JSON file.

return data.toJSONString();
 
	


}
}