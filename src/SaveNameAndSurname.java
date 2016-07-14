import java.io.*;


/**
 * Created by kde on 14.07.16.
 */


public class SaveNameAndSurname extends javax.servlet.http.HttpServlet {
    private int countDataRecord = 0;
    private String patchFile = "/home/kde/Develop/Web";
    private String fileName = "test.txt";

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String name;
        String surname;

        countDataRecord ++;
        name = request.getParameter("name");
        surname = request.getParameter("surname");

        if (name == null || surname == null) {
            System.out.println("error: data is null");
        } else if (!saveData(name + " " + surname)) {
            System.out.println("error: Data did not saved!");
        }

/// for debug
        PrintWriter out = response.getWriter();
        out.println("<h4>Saves per session:" + countDataRecord + "ed.</h4>");
        out.println("<h4>Data saved:</h4>");
        out.println(readData());
        out.close();
//
    }

    private boolean saveDataStream(String data) {

        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(patchFile + fileName, true))) {
            bufferedOutputStream.write(data.getBytes());
            bufferedOutputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean saveData(String data) {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(patchFile + fileName, true))) {
            bufferedWriter.write(data);
            bufferedWriter.append('\n');
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
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
}
