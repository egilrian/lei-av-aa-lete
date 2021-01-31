package com.egil.leiavaalete;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class LeteController {

    public static final String URL_CRAIGSLIST = "https://seattle.craigslist.org/d/for-sale/search/sss?query=";
    public static final String URL_NEXTDOOR = "https://nextdoor.com/for_sale_and_free/?init_source=more_menu&query=";
    public static final String URL_FINN = "https://www.finn.no/bap/forsale/search.html?q=";
    public static final String URL_GOOGLE = "http://google.com";

    /**
     * @param query example: "nikkor+1.4"
     * @return
     */
    @RequestMapping(value = "/lete")
    @GetMapping
    public String egilEndpoint(@RequestParam String site, @RequestParam String query) {
        StringBuffer content = new StringBuffer();
        try {
            String siteUrl = getSiteUrl(site);
            URL url = new URL(siteUrl + encodeQuery(query));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(GET.name());
            connection.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.flush();
            out.close();
            int responseCode = connection.getResponseCode();
            System.out.println("responseCode = " + responseCode);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            content = new StringBuffer();
            while ((inputLine = reader.readLine()) != null) {
                content.append(inputLine);
            }
            reader.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    private String encodeQuery(String query) {
        return query.replaceAll("\\+", "%20");
    }

    private String getSiteUrl(String site) {
        switch (site) {
            case "cl":
                return URL_CRAIGSLIST;
            case "nd":
                return URL_NEXTDOOR;
            case "fn":
                return URL_FINN;
            default:
                return URL_GOOGLE;
        }
    }
}
