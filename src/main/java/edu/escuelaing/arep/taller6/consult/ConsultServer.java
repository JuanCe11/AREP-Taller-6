package edu.escuelaing.arep.taller6.consult;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.secure;
import static spark.Spark.staticFiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ConsultServer {

	/**
	 * Main class that starts the main thread of the service.
	 * 
	 * @param args String array
	 */
	public static void main(String[] args) {
		port(getPort());
		staticFiles.location("/public");
		secure("keystores/clientkeypair.p12", "123456", null, null);
		get("/find", (req, res) -> {
			res.type("application/json");
			String ls_res;
			ls_res = "";
			ls_res = readURL("http://ec2-100-26-178-129.compute-1.amazonaws.com:35001/find");
			return ls_res;

		});
	}
	
	/**
	 * Reads a response from the server
	 * 
	 * @param as_site url to read
	 */
	private static String readURL(String as_site) {
        String ls_resData = null;
        try {
        	URL siteURL = new URL(as_site);
        	URLConnection urlConnection = siteURL.openConnection();
        	BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String inputLine = null;
            ls_resData = "";
            while ((inputLine = reader.readLine()) != null) {
                ls_resData += inputLine;
            }
        } catch (IOException x) {
            ls_resData = "";
            System.err.println(x);
        }
        return ls_resData;
    }

	/**
	 * Specifies the port on which the service will run.
	 * 
	 * @return The port where the service will be run.
	 */
	public static int getPort() {
		if (System.getenv("PORT") != null) {
			return Integer.parseInt(System.getenv("PORT"));
		}
		return 5001;
	}
}
