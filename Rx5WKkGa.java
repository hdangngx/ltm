
package rx5wkkga;
import java.io.*;
import java.net.Socket;

public class Rx5WKkGa {
    public static void main(String[] args) throws Exception {
        String serverHost  = "203.162.10.109";
        int    serverPort  = 2206;
        String studentCode = "B22DCVT138";
        String qCode       = "Rx5WKkGa";

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
        long sumPrime = 0;
        for (String part : parts) {
            int x = Integer.parseInt(part.trim());
            if (isPrime(x)) {
                sumPrime += x;
            }
        }

        writer.write(String.valueOf(sumPrime));
        writer.newLine();
        writer.flush();

        socket.close();
    }

    private static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2 || n == 3) return true;
        if (n % 2 == 0) return false;
        int limit = (int) Math.sqrt(n);
        for (int i = 3; i <= limit; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}