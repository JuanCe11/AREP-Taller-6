package edu.escuelaing.arep.taller6.login;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.port;
import static spark.Spark.secure;
import static spark.Spark.staticFiles;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.util.StringUtil;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class MyServer {
	
	/**
	 * Main class that starts the main thread of the service.
	 * 
	 * @param args String array
	 */
	public static void main(String[] args) {

		port(getPort());
		staticFiles.location("/public");
		secure("keystores/ecikeystore.p12", "19990611", null, null);

		get("/", (req, res) -> {
			Map<String, Object> lm_model;

			lm_model = new HashMap<String, Object>();
			lm_model.put("valid", true);

			return new ModelAndView(lm_model, "index.vm");
		}, new VelocityTemplateEngine());

		post("/login", (req, res) -> {
			Map<String, Object> lm_model;
			boolean lb_validate;

			lm_model = new HashMap<String, Object>();
			lb_validate = Login.login(req, res);
			lm_model.put("valid", lb_validate);

			return new ModelAndView(lm_model, "index.vm");
		}, new VelocityTemplateEngine());

		get("/app", (req, res) -> {
			Login.ensureUserIsLoggedIn(req, res);
			{
				Map<String, Object> lm_model;

				lm_model = new HashMap<String, Object>();
				lm_model.put("valid", true);

				return new ModelAndView(lm_model, "consultar.vm");
			}
		}, new VelocityTemplateEngine());

		get("/log", (req, res) -> {
			Login.ensureUserIsLoggedIn(req, res);
			{
				String ls_res;

				res.type("application/json");
				ls_res = URLReader.readURL("https://localhost:5001/find", true);

				if (StringUtil.isBlank(ls_res))
					ls_res = "{\"message\": \"Error en la consulta\"}";

				return ls_res;
			}

		});

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
		return 5000;
	}
}