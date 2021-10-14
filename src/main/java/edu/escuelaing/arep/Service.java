package edu.escuelaing.arep;

import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Service {
    public static void main(String[] args) {
        port(getPort());

        get("/exp", "application/json", (req, res) -> {
            res.type("application/json");
            return processRequest("exp", req.queryParams("value"));
        });

        get("/atan", "application/json", (req, res) -> {
            res.type("application/json");
            return processRequest("atan", req.queryParams("value"));
        });
    }

    public static void RoundRobbin() {

    }

    public static String processRequest(String operation, String value) throws IOException {
        URL url;
        String res = " Not found ";
        try {
            url = new URL("http://localhost:4567/" + operation + "?value=" + value);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            res = content.toString();
            return res;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return res;
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4568;
    }
}
