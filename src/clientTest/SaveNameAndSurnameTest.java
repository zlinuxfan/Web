package clientTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by User on 11.08.2016.
 */
public class SaveNameAndSurnameTest {

    public static void main(String[] args) throws IOException {
        testRightUrl(3);
        testNullPapametr(3);
        testNullPapametrs(3);
    }

    private static void testNullPapametrs(int testNum) {
        for (int threadNum = 0; threadNum < 10; threadNum ++) {
            Runnable runnable = new doRequest("http://localhost:8080/info?name&surname");
            Thread thread = new Thread(runnable);
            thread.run();
        }
    }

    private static void testNullPapametr(int testNum) {
        for (int threadNum = 0; threadNum < testNum; threadNum ++) {
            Runnable runnable = new doRequest("http://localhost:8080/info?name=&surname=nimble" + threadNum);
            Thread thread = new Thread(runnable);
            thread.run();
        }
    }

    private static void testRightUrl(int testNum) {
        for (int threadNum = 0; threadNum < testNum; threadNum ++) {
            Runnable runnable = new doRequest("http://localhost:8080/info?name=serj" + threadNum + "&surname=nimble" + threadNum);
            Thread thread = new Thread(runnable);
            thread.run();
        }
    }

    private static class doRequest implements Runnable {
        private String urlToRead;

        public doRequest(String urlToRead) {
            this.urlToRead = urlToRead;
        }

        @Override
        public void run() {
            String line;
            URL url;
            HttpURLConnection urlConnection;

            try {
                url = new URL(urlToRead);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
