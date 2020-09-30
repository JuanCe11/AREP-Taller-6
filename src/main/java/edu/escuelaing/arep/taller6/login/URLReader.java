package edu.escuelaing.arep.taller6.login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 *	Class that reads an url and configure the trust store
 */
public class URLReader {

	/**
	 * Configures the trust store to do the request
	 */
	private static void config() {
		try {
			File trustStoreFile = new File("keystores/clientStore");
			System.out.println(trustStoreFile.getAbsolutePath());
			char[] trustStorePassword = "123456".toCharArray();

			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);

			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

			tmf.init(trustStore);

			for (TrustManager t : tmf.getTrustManagers()) {
				System.out.println(t);
			}

			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, tmf.getTrustManagers(), null);
			SSLContext.setDefault(sslContext);

		} catch (KeyStoreException ex) {
			Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
		} catch (CertificateException ex) {
			Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
		} catch (KeyManagementException ex) {
			Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	/**
	 * Reads an response from the server
	 * 
	 * @param as_site url to read
	 * @param lb_config if is necessary to config the trust store
	 * @return A string with the body response 
	 */
	public static String readURL(String as_site, boolean lb_config) {
		if (lb_config)
			config();
		String ls_resData;
		ls_resData = null;
		try {
			URL lu_siteURL;

			lu_siteURL = new URL(as_site);

			if (lu_siteURL != null) {
				URLConnection luc_urlConnection;

				luc_urlConnection = lu_siteURL.openConnection();

				if (luc_urlConnection != null) {
					InputStreamReader lis_input;

					lis_input = new InputStreamReader(luc_urlConnection.getInputStream());

					if (lis_input != null) {
						BufferedReader lbr_reader;

						lbr_reader = new BufferedReader(lis_input);

						if (lbr_reader != null) {
							String ls_line;

							ls_line = null;

							ls_resData = "";

							while ((ls_line = lbr_reader.readLine()) != null)
								ls_resData += ls_line;

						}
					}

				}
			}
		} catch (IOException x) {
			ls_resData = "";
			x.printStackTrace();
		}

		return ls_resData;
	}

}