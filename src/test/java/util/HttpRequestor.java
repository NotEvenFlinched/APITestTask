package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestor {
    /**
     * this method indicates to do a Get request
     *
     * @param route - url
     * @return response - JSON content type
     */
    public static String get(String route) {
        try {
            String response = "";
            URL url = new URL(route);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.err.println("HTTP error code : " + conn.getResponseCode() + " Error message:" + conn.getResponseMessage());
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                response = output;
            }

            conn.disconnect();
            return response;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * This method indicates to do a Post request
     *
     * @param route - url
     * @param requestBody - JSON content type
     * @return response - JSON content type
     */
    public static String post(String route, String requestBody) {
        try {
            String response = "";
            URL url = new URL(route);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.err.println("HTTP error code : " + conn.getResponseCode() + " Error message:" + conn.getResponseMessage());
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = "";
            while ((output = br.readLine()) != null) {
                response = output;
            }

            conn.disconnect();
            return response;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
