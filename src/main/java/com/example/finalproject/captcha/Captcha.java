package com.example.finalproject.captcha;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The Captcha class is responsible for verifying whether a captcha was passed by the user. It uses Google's reCAPTCHA.
 */
public class Captcha {
    /**
     * The method checks if the captcha was passed
     *
     * @param request HttpServletRequest
     * @return Result about success
     */
    public static boolean isCaptchaPassed(HttpServletRequest request) throws IOException {
        boolean success = false;
        String secretKey = "6Lca-40lAAAAAGZLMNSwbI6FRy9Wre9DLhCiI2vU";
        String response = request.getParameter("g-recaptcha-response");
        String url = "https://www.google.com/recaptcha/api/siteverify";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        String postParams = "secret=" + secretKey + "&response=" + response;
        con.setDoOutput(true);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
        outputStreamWriter.write(postParams);
        outputStreamWriter.flush();
        outputStreamWriter.close();
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder responseBuffer = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                responseBuffer.append(inputLine);
            }
            in.close();
            JsonElement jsonElement = JsonParser.parseString(responseBuffer.toString());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            success = jsonObject.get("success").getAsBoolean();
        }
        return success;
    }
}
