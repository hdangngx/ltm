package qnvkokak;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class QNVkoKak {
    public static void main(String[] args) throws Exception {
        String serverHost  = "203.162.10.109";
        int    serverPort  = 2207;
        String studentCode = "B22DCVT138";
        String qCode       = "qNVkoKak";

        Socket socket = new Socket(serverHost, serverPort);
        socket.setSoTimeout(5000);

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream  dis = new DataInputStream(socket.getInputStream());

        dos.writeUTF(studentCode + ";" + qCode);
        dos.flush();

        String data = dis.readUTF();
        if (data == null || data.isEmpty()) {
            socket.close();
            return;
        }

        String[] parts = data.split(",");
        int n = parts.length;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(parts[i].trim());
        }

        int changeCount = 0;
        int totalVariation = 0;
        int prevSign = 0;

        for (int i = 0; i < n - 1; i++) {
            int diff = a[i + 1] - a[i];
            int absDiff = diff >= 0 ? diff : -diff;
            totalVariation += absDiff;

            if (diff != 0) {
                int sign = diff > 0 ? 1 : -1;
                if (prevSign != 0 && sign != prevSign) {
                    changeCount++;
                }
                prevSign = sign;
            }
        }

        dos.writeInt(changeCount);
        dos.writeInt(totalVariation);
        dos.flush();

        socket.close();
    }
}