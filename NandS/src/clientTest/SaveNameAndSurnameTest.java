package clientTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
        
    	testRightUrl(5);
    }

    
    private static void testRightUrl(int testNum) {
        
        	Thread[] threads = new Thread[testNum];
                  	
        	
            for (int i = 0; i < threads.length; i++)
            {
            	threads[i] = new Thread(new doRequest("http://localhost:8080/webApp/info?name=serj" + i + "&surname=nimble" + i));
            	threads[i].setName("Thread[" + i +"]");
            	threads[i].start();
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
            int errorNums = 0;

            try {           	
            	for (int i = 0; i < 500; i++) {
	                url = new URL(urlToRead);
	                urlConnection = (HttpURLConnection) url.openConnection();
	                urlConnection.setRequestMethod("GET");
	                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	
	                while ((line = reader.readLine()) != null) {
	                    if (!line.equals("Data saved")) {
	                    	errorNums ++;
	                    }
	                }

            	}
            	saveLog(errorNums, Thread.currentThread().getName());
            	

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

		private void saveLog(int errorNums, String threadName) throws IOException {
			String fullFileName = System.getProperty("user.dir") + "/log";
	           
	        File file = new File(fullFileName);
	            if (!file.exists()) {
	            	// If file not exists, create file System.getProperty("user.dir") + "/fileName";
	                if (!file.createNewFile()) {
	                    throw new IOException("Can`t create log file.");
	                }
	            }

	        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fullFileName, true))) {
	            bufferedWriter.write(threadName + ": " + errorNums + " err.");
	            bufferedWriter.append('\n');
	        } catch (IOException e) {
	            throw new IOException("Error write log.");
	        }
		}
    }
}
