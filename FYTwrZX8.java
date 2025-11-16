
package bai4_tcpsum;


import java.io.*;
import java.net.*;

public class Bai4_TCPSum {
    public static void main(String[] args) throws Exception {
        String serverHost  = "203.162.10.109"; 
        int    serverPort  = 2206;
        String studentCode = "B22DCVT138";      
        String qCode       = "FYTwrZX8";

       
        Socket socket = new Socket(serverHost, serverPort);
        socket.setSoTimeout(5000);

     
        InputStream  is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

       
        writer.write(studentCode + ";" + qCode);
        writer.newLine();
        writer.flush();

    
        String data = reader.readLine();  
       
        long sum = 0;
        if (data != null && !data.isEmpty()) {
            String[] parts = data.split("\\|");
            for (String part : parts) {
                part = part.trim();
                if (!part.isEmpty()) {
                    sum += Long.parseLong(part);
                }
            }
        }

       
        writer.write(String.valueOf(sum));
        writer.newLine();
        writer.flush();

       
        socket.close();
    }
}