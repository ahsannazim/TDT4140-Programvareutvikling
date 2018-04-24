package tdt4140.gr1878.web.server.jaxrs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import tdt4140.gr1878.web.server.rest.JSONz;

//Primitiv handler, egentlig skal implementeres som JSONService
public class RequestHandler extends AbstractHandler {
	final String hil;
	final String body;

	public RequestHandler() throws Exception {
		//Skulle egentlig hente fra db gjennom server her/json filer. TODO, hvis mer vi hadde mer tid
		this("Data fra statens vegvesen", " {\n" + 
				"    \"navn\" : \"Jan\",\n" + 
				"    \"locations\" : [\n" + 
				"      { \"latitude\" : \"63\", \"longitude\" : \"10\" },\n" + 
				"      [ \"63.1\", \"10.1\" ]\n" + 
				"    ]\n" + 
				"  },");
	}


	public RequestHandler(String greeting) {
		this(greeting, null);
	}

	public RequestHandler(String greeting, String body) {
		this.hil = greeting;
		this.body = body;
	}

	//Egentlig burde dette vært secured med SHA evt. public private key handshake exchange.
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		PrintWriter out = response.getWriter();
		
	//Skulle egentlig ha deserialized og serialized json hvis mer tid.. Eventult wrapped data fra cords og db 
		out.println("<h1>" + hil + "</h1>");
		if (body != null) {
			out.println(body);
		}

		baseRequest.setHandled(true);
	}
}