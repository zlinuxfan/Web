package com;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Properties;




public class SaveNameAndSurname extends javax.servlet.http.HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4678467854L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name;
        String surname;
        boolean errorStatus = true;

        name = request.getParameter("name");
        surname = request.getParameter("surname");
     
        try {        
	        if (!name.isEmpty()) {
	            if (!surname.isEmpty()) {
	                 saveData(name + " " + surname);
	            } else {
	                throw new NullPointerException("Parametrs can`t be null...");
	            }
	        } else {
	            throw new NullPointerException("Parametrs can`t be null.");
	        }
        } catch (NullPointerException e) {
        	Date date = new Date();
			saveLog(date.toString() + ": Parametrs name and surname can`t be null." + " Name: " + name + ", surname: " + surname);
			errorStatus = false;
		}

        writeResponse(response, errorStatus);
    }

    private void saveData(String data) throws IOException {

        String fullFileName = getFullFileName();
           
        File file = new File(fullFileName);
            if (!file.exists()) {
            	// If file not exists, create file System.getProperty("user.dir") + "/fileName";
                if (!file.createNewFile()) {
                    throw new IOException("Can`t create file.");
                }
            }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fullFileName, true))) {
            bufferedWriter.write(data);
            bufferedWriter.append('\n');
        } catch (IOException e) {
        	saveLog("Error write!. Parametrs [" + data + "] do not save.");
        }
    }

    private String getFullFileName() {
    	String patch = "/webapps/webApp/WEB-INF/config.properties";
        Properties fileProperties = new Properties();
        
        try (FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + patch)) {
            fileProperties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileProperties.getProperty("fileName");
    }

    private void writeResponse(HttpServletResponse response, boolean errorStatus) throws IOException {
        PrintWriter out = response.getWriter();
        if (errorStatus) {
        	out.println("Data saved");
        } else {
        	out.println("Error. Data did not saved");
        }
        
        out.close();
    }
    
    private void saveLog(String error) throws IOException {
    	String fullFileName = System.getProperty("user.dir") + "/log";
        
        File file = new File(fullFileName);
            if (!file.exists()) {
            	// If file not exists, create file System.getProperty("user.dir") + "/fileName";
                if (!file.createNewFile()) {
                    throw new IOException("Can`t create log file.");
                }
            }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fullFileName, true))) {
            bufferedWriter.write(error);
            bufferedWriter.append('\n');
        } catch (IOException e) {
            throw new IOException("Error write log.");
        }
    }
}
