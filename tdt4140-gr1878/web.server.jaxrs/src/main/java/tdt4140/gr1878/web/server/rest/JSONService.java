package tdt4140.gr1878.web.server.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import tdt4140.gr1878.app.core.GeoLocation;

//Rest service, ikke ferdig implementert. Hvis vi fikk mer tid til dette og fikk ordnet den erroren ville 
//dette vært ferdig.. (spurte studass, fikk samme jetty feil på alle prosjektene til og med eksempel prosjektet
//til Halvard

@Path("/json/QQ")
public class JSONService {

	@GET
	@Path("/get")
	@Produces("application/json")
	public GeoLocation getCords() {
		GeoLocation location = new GeoLocation();
		//Set... 
		
		return location; 

	}

	@POST
	@Path("/post")
	@Consumes("application/json")
	public Response createCordsInJSON(GeoLocation location) {
		String result = "Location added : " + location;
		//..
		return Response.status(201).entity(result).build();
	}
	
	//Her skulle vi ha hatt gitt statensvegvesen mulighet til å hente data fra serveren gjennom at serveren
	//handler en request fks: /getdata og henter data til serveren fra databasen
	
}