package edu.escuelaing.arep.taller6;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.secure;

public class App 
{
	/**
	 * Main class that starts the main thread of the service.
	 * 
	 * @param args String array
	 */
    public static void main( String[] args )
    {
    	port(getPort());
    	secure("keystores/ecikeystore.p12", "19990611", null, null); 
		get("/", (req,res) ->"Hola mundo c:");
    }
    
	/**
	 * Specifies the port on which the service will run.
	 * 
	 * @return The port where the service will be run.
	 */
	public static int getPort() {    
		if (System.getenv("PORT") != null)
		{            
			return Integer.parseInt(System.getenv("PORT"));      
		} 
		return 5000; 
	}
}
