package clientTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by User on 11.08.2016.
 */
public class SaveNameAndSurnameTest {

    public static void main(String[] args) throws IOException {
        doRequest("http://localhost:8080/info?name=serj1&surname=nimble1");
    }

    private static boolean doRequest(String urlToRead) throws IOException {
        String response = "";
        String line;
        URL url = new URL(urlToRead);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        while ((line = reader.readLine()) != null) {
            response += line;
        }
        System.out.println(response);
        return true;
    }
}
