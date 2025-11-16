
package bai5_tcpdatastream;

import java.io.*;
import java.net.*;

public class Bai5_TCPDataStream {
    public static void main(String[] args) throws Exception {
        String serverHost  = "203.162.10.109";
        int    serverPort  = 2207;
        String studentCode = "B15DCCN999"; // thử dùng đúng mẫu đề
        String qCode       = "1D25ED92";   // thử đúng mã trong ví dụ

        Socket socket = new Socket(serverHost, serverPort);
        socket.setSoTimeout(5000);

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream  dis = new DataInputStream(socket.getInputStream());

        dos.writeUTF(studentCode + ";" + qCode);
        dos.flush();

        int a = dis.readInt();
        int b = dis.readInt();

        int sum = a + b;
        int product = a * b;

        dos.writeInt(sum);
        dos.writeInt(product);
        dos.flush();

        socket.close();
    }
}