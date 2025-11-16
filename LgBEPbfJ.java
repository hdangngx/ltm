/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lgbepbfj;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class LgBEPbfJ {
    public static void main(String[] args) throws Exception {
        String serverHost  = "203.162.10.109";
        int    serverPort  = 2207;
        String studentCode = "B22DCVT138";
        String qCode       = "lgBEPbfJ";

        Socket socket = new Socket(serverHost, serverPort);
        socket.setSoTimeout(5000);

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream  dis = new DataInputStream(socket.getInputStream());

        dos.writeUTF(studentCode + ";" + qCode);
        dos.flush();

        int k = dis.readInt();
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

        for (int start = 0; start < n; start += k) {
            int left = start;
            int right = Math.min(start + k - 1, n - 1);
            while (left < right) {
                int tmp = a[left];
                a[left] = a[right];
                a[right] = tmp;
                left++;
                right--;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(",");
            sb.append(a[i]);
        }

        dos.writeUTF(sb.toString());
        dos.flush();

        socket.close();
    }
}