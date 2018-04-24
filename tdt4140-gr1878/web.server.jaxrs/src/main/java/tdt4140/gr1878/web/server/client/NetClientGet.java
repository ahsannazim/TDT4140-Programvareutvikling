package tdt4140.gr1878.web.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetClientGet {
	//Client get request, ikke ferdig implementert, samme jetty error når serveren kjøres (505) uten grunn.
	//Disse skulle egentlig være under handler og en skulle være setup se ServerA for full forklaring
	public static void main(String[] args) {
		try {
			URL url = new URL("http://localhost:8080/get");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			String output;
			System.out.println("Out \n");
			while ((output = br.readLine()) != null) {

				System.out.println(output);
			}
			
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
			
		}

	}

}