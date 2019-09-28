package com.pb.hackathon.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {

    static String BASE_URL = "https://truship-backend.herokuapp.com/api";

    public static String httpPost(String appendUrl, String body)  {
        StringBuffer result = new StringBuffer("");
        try {
            URL url = new URL(BASE_URL + appendUrl);
            // 1. create HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            // 2. add JSON content to POST request body
            setPostRequestContent(conn, body);

            // 4. make POST request to the given URL
            conn.connect();

            // 5. return response message
            return readResponse(result, conn);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong please try again.");
        }
    }

    public static String httpGet(String appendUrl)  {
        StringBuffer result = new StringBuffer("");
        try {
            URL url = new URL(BASE_URL + appendUrl);
            // 1. create HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            // 4. make POST request to the given URL
            conn.connect();

            // 5. return response message
            return readResponse(result, conn);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong please try again.");
        }
    }


    private static String readResponse(StringBuffer result, HttpURLConnection conn) throws IOException {
        if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
            InputStream is = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(is));
            String readLine = null;
            while ((readLine = bufferedReader.readLine()) != null) {
                result.append(readLine);
            }
            conn.disconnect();
        } else {
            throw new RuntimeException("Something went wrong please try again.");
        }
        return result.toString();
    }

    private static void setPostRequestContent(HttpURLConnection conn, String jsonString) throws IOException {
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonString);
        writer.flush();
        writer.close();
        os.close();
    }
}
