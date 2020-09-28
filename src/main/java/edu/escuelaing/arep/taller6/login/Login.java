package edu.escuelaing.arep.taller6.login;

import java.nio.charset.StandardCharsets;

import org.eclipse.jetty.util.StringUtil;

import com.google.common.hash.Hashing;

import spark.*;

public class Login {

	/** Property login path. */
	public static final String LOGIN_PATH = "/";

	/** Property app path. */
	public static final String APP_PATH = "/app";

	/** Property login user. */
	public static final String USER = "user";

	/** Property login pass. */
	public static final String PASS = "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92";

	/**
	 * Validates if an user is logged in
	 * 
	 * @param lr_request
	 * @param lr_response
	 */
	public static void ensureUserIsLoggedIn(Request lr_request, Response lr_response) {
		if (lr_request.session().attribute("currentUser") == null) {
			lr_response.redirect(LOGIN_PATH);
		}
	};

	/**
	 * Validates the user and password
	 * 
	 * @param ar_request  the request
	 * @param ar_response the response
	 * @return If the user and password are correct
	 */
	public static boolean login(Request ar_request, Response ar_response) {

		boolean lb_res;

		lb_res = false;

		if (ar_request != null && ar_response != null) {
			String ls_pas;
			String ls_usr;

			ls_pas = ar_request.queryParams("pass");
			ls_usr = ar_request.queryParams("usr");

			if (StringUtil.isNotBlank(ls_pas) && StringUtil.isNotBlank(ls_usr)) {
				lb_res = ls_usr.equals(USER)
						&& Hashing.sha256().hashString(ls_pas, StandardCharsets.UTF_8).toString().equals(PASS);

				if (lb_res) {
					ar_request.session().attribute("currentUser", ls_usr);
					ar_response.redirect(APP_PATH);
				}

			}
		}

		return lb_res;

	}

}
