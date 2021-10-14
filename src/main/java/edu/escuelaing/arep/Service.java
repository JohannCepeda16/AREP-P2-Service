package edu.escuelaing.arep;

import static spark.Spark.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Service {

    private static List<ServiceStatus> services = new ArrayList<ServiceStatus>();

    public static void main(String[] args) {

        services.add(new ServiceStatus("http://localhost:4567/", true));
        services.add(new ServiceStatus("http://localhost:4566/", true));

        port(getPort());
        get("/exp", "application/json", (req, res) -> {
            res.type("application/json");
            return roundRobbin("exp", req.queryParams("value"));
        });

        get("/atan", "application/json", (req, res) -> {
            res.type("application/json");
            return roundRobbin("atan", req.queryParams("value"));
        });
    }

    public static String roundRobbin(String operation, String value) throws IOException {
        String res = "";
        if (services.get(0).isActive()) {
            services.get(0).setActive(!services.get(0).isActive());
            res = processRequest(services.get(0).getUrl(), operation, value);
        } else if (services.get(1).isActive()) {
            services.get(0).setActive(!services.get(0).isActive());
            res = processRequest(services.get(1).getUrl(), operation, value);
        }

        return res;
    }

    public static String processRequest(String urlQuery, String operation, String value) throws IOException {
        URL url;
        String res = " Not found ";
        try {
            url = new URL(urlQuery + operation + "?value=" + value);
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
