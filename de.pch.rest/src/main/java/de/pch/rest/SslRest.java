package de.pch.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SslRest {
	
    private SSLSocketFactory sslSocketFactory = null;
    
    private String token = "";
    
    protected String basicUrl = "";
    
    public String getBasicUrl() {
		return basicUrl;
	}

	public void setBasicUrl(String basicUrl) {
		this.basicUrl = basicUrl;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    
    public String getJson(String path) throws Exception {
    	String json = null;
        BufferedReader reader = null;

        try {
        	URL url = new URL(basicUrl + path);
            HttpURLConnection httpConnection = (HttpsURLConnection) url.openConnection();

            setAcceptAllVerifier((HttpsURLConnection)httpConnection);

            httpConnection.addRequestProperty("Content-Type","application/json");
            httpConnection.addRequestProperty("Authorization","Bearer " + token);
            reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()), 1);

            char[] buf = new char[1024];
            StringBuilder sb = new StringBuilder();
            int count = 0;
            while( -1 < (count = reader.read(buf)) ) {
                sb.append(buf, 0, count);
            }
            json = sb.toString();


        } catch (IOException ex) {
            System.out.println(ex);

        } finally {
        	if (reader != null) {
	            reader.close();
	            reader = null;
        	}
        }
        

    	return json;
    }
	/**
     * Overrides the SSL TrustManager and HostnameVerifier to allow
     * all certs and hostnames.
     * WARNING: This should only be used for testing, or in a "safe" (i.e. firewalled)
     * environment.
     * 
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    protected void setAcceptAllVerifier(HttpsURLConnection connection) throws NoSuchAlgorithmException, KeyManagementException {

        // Create the socket factory.
        // Reusing the same socket factory allows sockets to be
        // reused, supporting persistent connections.
        if( null == sslSocketFactory) {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, ALL_TRUSTING_TRUST_MANAGER, new java.security.SecureRandom());
            sslSocketFactory = sc.getSocketFactory();
        }

        connection.setSSLSocketFactory(sslSocketFactory);

        // Since we may be using a cert with a different name, we need to ignore
        // the hostname as well.
        connection.setHostnameVerifier(ALL_TRUSTING_HOSTNAME_VERIFIER);
    }

    private static final TrustManager[] ALL_TRUSTING_TRUST_MANAGER = new TrustManager[] {
        new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {}
            public void checkServerTrusted(X509Certificate[] certs, String authType) {}
        }
    };

    private static final HostnameVerifier ALL_TRUSTING_HOSTNAME_VERIFIER  = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };
}
