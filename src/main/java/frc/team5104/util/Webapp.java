/* BreakerBots Robotics Team (FRC 5104) 2020 */
package frc.team5104.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import frc.team5104.util.console.c;

@SuppressWarnings("restriction")
public class Webapp {
	private static HttpServer server;

	public static boolean run() {
		try {
			File dir = new File(getBaseUrl());
			if (dir.exists() == false) {
				console.log(c.WEBAPP, "Webapp not on roboRio. Please deploy the webapp using `webapp deploy`");
				return false;
			}
						
			//Setup Server
			int port = 5804; //has to be between 5800-5810 (5800,5801 for limelight)
			server = HttpServer.create(new InetSocketAddress(port), 0);
			
			//Web App URLs	
			server.createContext("/", new PageRequestHandler("app.html")); 
			server.createContext("/resources/", new FilesRequestHandler());
			
			//Web App Requests
			server.createContext("/tuner", new TunerHandler());
			
			//Start Server
			server.setExecutor(null);
			server.start();
			
			console.log(c.WEBAPP, "Hosting Web App at 10.51.4.2:" + port);
			
			return true;
		} catch (Exception e) { 
			console.error(c.WEBAPP, e);  
			return false;
		}
	}
	
	private static String getBaseUrl() {
		String u = "/home/lvuser/webapp/";
		return u;
	}
	
	private static class RequestHandler implements HttpHandler {
		public String fileUrl;
		public String contentType;
		
		public RequestHandler(String fileUrl, String contentType) {
			this.fileUrl = fileUrl;
			this.contentType = contentType;
		}
		
		public void handle(HttpExchange t) throws IOException {
			readSendFile(t, fileUrl, contentType);
		}
		
		void readSendFile(HttpExchange t, String fileUrl, String contentType) throws IOException {
			String finalUrl = getBaseUrl() + fileUrl;
			
			//Header
			Headers h = t.getResponseHeaders();
			h.add("Content-Type", contentType);

			//Read
			File file = new File(finalUrl);
			FileInputStream input = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(input);
			byte[] bytes = new byte[input.available()];
			bis.read(bytes, 0, bytes.length);
			input.close();

			//Send
			t.sendResponseHeaders(200, bytes.length);
			OutputStream os = t.getResponseBody();
			os.write(bytes, 0, bytes.length);
			os.close();
		}
	}
	
	private static class PageRequestHandler extends RequestHandler {
		public PageRequestHandler(String fileUrl) {
			super(fileUrl, "text/html");
		}
	}
	
	private static class FilesRequestHandler extends RequestHandler {
		public static enum fileHeaders {
			js("application/javascript"),
			css("text/css"),
			png("image/png");
			String type; fileHeaders(String type) { this.type = type; }
		}
		
		public FilesRequestHandler() {
			super(null, null);
		}
		
		public void handle(HttpExchange t) throws IOException {
			String url = t.getRequestURI().toString();
			String contentType = url.substring(url.indexOf('.') + 1);
			contentType = fileHeaders.valueOf(contentType).type;
			
			readSendFile(t, url, contentType);
		}
	}
	
	private static class TunerHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			t.getResponseHeaders().add("Content-Type", "application/json");
			
			String requestType = t.getRequestURI().toString().substring(7);
			
			//Init
			if (requestType.equals("init")) {
				//Send headers
				String response = WebappTuner.getInit();
				
				t.sendResponseHeaders(200, response.length());
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			}
			
			//Get
			else if (requestType.equals("get")) {
				//Send outputs
				String response = WebappTuner.getOutputs();
				
				t.sendResponseHeaders(200, response.length());
	            OutputStream os = t.getResponseBody();
	            os.write(response.getBytes());
	            os.close();
			}
			
			//Set
			else if (requestType.equals("set")) {
				//Set inputs
				InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
            	BufferedReader br = new BufferedReader(isr);
            	String data = br.readLine();
            	
            	data = data.substring(1, data.length() - 1);
            	
            	String name = data.substring(data.indexOf("\"name\":\"")+8, data.indexOf(',')-1);
            	String value = data.substring(data.indexOf("\"value\":\"")+9, data.length()-1);
            	
            	WebappTuner.handleInput(name, value);
			}
		}
	}
}
