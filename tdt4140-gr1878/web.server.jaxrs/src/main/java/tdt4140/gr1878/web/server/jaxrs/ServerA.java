package tdt4140.gr1878.web.server.jaxrs;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ServerA {
	
	/*Ble ikke ferdig med serveren blant annet på grunn av en jetty error hvor serveren ikke kunne kjøres
	 *og dermed måtte vi compile/kjøre integration tests får å ha serveren kjørende. Jetty out of bounds exception
	 *og connection refused når vi prøvde å koble til databasen gjennom serveren
	 *selv om vi var koblet til skolenettet og VPN.
	 *
	 *Viser til kode som funker og kommenterer bort + legger til kode og forklaringer på hvordan vi ville
	 *ha gjort det hvis vi fikk bedre tid. Det var blant annet grunnet til sein tilbakemelding på spintene 
	 * 
	 * 
	 */
	Xhandler handler;
	
	//Basic setup, egentlig kan dette gjøres slikt at det blir satt opp av jetty under build.
	public ServerA(int port, String contextEnd) throws IOException {
		  Server server = new Server(8080);
          try {
              server.getConnectors()[0].getConnectionFactory(HttpConnectionFactory.class);
              server.setHandler(new RequestHandler());
              server.start();
              server.join();
          } catch (Exception e) {
              e.printStackTrace();
          }
	}
	//basic init /test
	public ServerA() throws IOException {
		this(8080, "/test");
	}
	
    private static class Xhandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Connected";
            //Trenger ikke dette siden ingenting på serveren 
            // String[] returnedD = convertStreamToArray(t.getRequestBody());
            //this.map = createHashMap(returnedD);
            //System.out.println(returnedD);
            
            try {
            //Handler her, kan lage ny class
			//	RequestHandler handler = new RequestHandler(map);
			//	handler.handle();
			} catch (Exception e) {
				System.out.println(e);
			}
            // Testing:
            //viewArray(posted);
            //viewHashMap(this.map);
            
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        
    public static void main(String[] args) throws Exception {   
        ServerA server = new ServerA();
    }
}
}