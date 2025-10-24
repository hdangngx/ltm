package thuchanhlaptrinhmang1;
import java.io.*;
import java.net.*;

public class Thuchanhlaptrinhmang1 {
    public static void main(String[] args) throws Exception {
        String serverHost = "203.162.10.109";
        int serverPort = 2206;
        String studentCode = "B22DCVT138";
        String qCode = "pZCsTEQO";

       
            Socket socket = new Socket(serverHost, serverPort);

            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            out.write((studentCode + ";" + qCode).getBytes());
            out.flush();

            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            String received = new String(buffer, 0, bytesRead);

            String[] nums = received.trim().split("\\|");
            int sum = 0;
            for (String num : nums) sum += Integer.parseInt(num.trim());

            out.write(String.valueOf(sum).getBytes());
            out.flush();

            socket.close();
       
        }
    }
