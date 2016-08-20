import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Properties;

/**
 * Created by kde on 14.07.16.
 */


public class SaveNameAndSurname extends javax.servlet.http.HttpServlet {
    private int countDataRecord = 0;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String name;
        String surname;

        this.countDataRecord ++;
        name = request.getParameter("name");
        surname = request.getParameter("surname");

        if (!name.isEmpty()) {
            if (!surname.isEmpty()) {
                saveData(name + " " + surname);
            } else {
                throw new NullPointerException("Parametrs can`t be null.");
            }
        } else {
            throw new NullPointerException("Parametrs can`t be null.");
        }

/// for debug
        debug(response);
//
    }

    private void saveData(String data) throws IOException {

        String fullFileName = getFullFileName();

        File file = new File(fullFileName);
            if (!file.exists()) {
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
        Properties fileProperties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("f:\\Web\\src\\resources\\config.properties")) {
            fileProperties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileProperties.getProperty("filePatch") + fileProperties.getProperty("fileName");
    }

    /// for debug
    private void debug(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<h4>Saves per session:" + this.countDataRecord + "ed.</h4>");
        out.println("<h4>Data saved:</h4>");
        out.println(readData());
        out.close();
    }

    private String readData() {

        String outData = "";
        String lineData;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(getFullFileName()))) {
            while ((lineData = bufferedReader.readLine()) != null) {
                outData += lineData + " ";
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return outData;
    }
    ///
}
