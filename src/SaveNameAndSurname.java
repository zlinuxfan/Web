import javax.servlet.http.HttpServletResponse;
import java.io.*;
/**
 * Created by kde on 14.07.16.
 */


public class SaveNameAndSurname extends javax.servlet.http.HttpServlet {
    private int countDataRecord = 0;
    private String patchFile = "f:\\Web\\";
    private String fileName = "data.txt";

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String name;
        String surname;

        countDataRecord ++;
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

        String fullFileName = patchFile + fileName;

        File file = new File(fullFileName);
            if (!file.exists()) {
                if (file.createNewFile()) {
                    throw new IOException("Can`t create file.");
                }
            }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fullFileName, true));
            bufferedWriter.write(data);
            bufferedWriter.append('\n');
            bufferedWriter.close();
    }

    /// for debug
    private void debug(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<h4>Saves per session:" + countDataRecord + "ed.</h4>");
        out.println("<h4>Data saved:</h4>");
        out.println(readData());
        out.close();
    }

    private String readData() {

        String outData = "";
        String lineData;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(patchFile + fileName))) {
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
