package com;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

        name = request.getParameter("name");
        surname = request.getParameter("surname");
     
        if (!name.isEmpty()) {
            if (!surname.isEmpty()) {
                 saveData(name + " " + surname);
            } else {
                throw new NullPointerException("Parametrs can`t be null...");
            }
        } else {
            throw new NullPointerException("Parametrs can`t be null.");
        }

        writeResponse(response);
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
            throw new IOException("Error write data.");
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

    private void writeResponse(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("Data saved");
        
        out.close();
    }
}
