package bai15_tcpbytestream;

import java.io.*;
import java.net.Socket;

public class Bai15_TCPByteStream {
    public static void main(String[] args) throws Exception {
        String serverHost  = "203.162.10.109";
        int    serverPort  = 2206;
        String studentCode = "B22DCVT138";
        String qCode       = "aLnmzXiA";

        Socket socket = new Socket(serverHost, serverPort);
        socket.setSoTimeout(5000);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));

        writer.write(studentCode + ";" + qCode);
        writer.newLine();
        writer.flush();

        String data = reader.readLine();
        if (data == null || data.isEmpty()) {
            socket.close();
            return;
        }

        String[] parts = data.split(",");
        int n = parts.length;
        int[] a = new int[n];
        long sum = 0;
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(parts[i].trim());
            sum += a[i];
        }

        double avg = (double) sum / n;
        double target = 2 * avg;

        double bestDiff = Double.MAX_VALUE;
        int num1 = a[0], num2 = a[1];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int s = a[i] + a[j];
                double diff = Math.abs(s - target);
                if (diff < bestDiff) {
                    bestDiff = diff;
                    if (a[i] < a[j]) {
                        num1 = a[i];
                        num2 = a[j];
                    } else {
                        num1 = a[j];
                        num2 = a[i];
                    }
                }
            }
        }

        String result = num1 + "," + num2;
        writer.write(result);
        writer.newLine();
        writer.flush();

        socket.close();
    }
}