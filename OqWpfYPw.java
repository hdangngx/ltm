
package bai10_tcpproduct;

import java.io.*;
import java.net.*;
import TCP.Product;

public class Bai10_TCPProduct {
    public static void main(String[] args) throws Exception {
        String serverHost  = "203.162.10.109";
        int    serverPort  = 2209;
        String studentCode = "B22DCVT138";
        String qCode       = "OqWpfYPw";

        Socket socket = new Socket(serverHost, serverPort);
        socket.setSoTimeout(5000);

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream  ois = new ObjectInputStream(socket.getInputStream());

        oos.writeUTF(studentCode + ";" + qCode);
        oos.flush();

        Product p = (Product) ois.readObject();

        long n = (long) Math.floor(p.getPrice());
        n = Math.abs(n);
        int discount = 0;
        while (n > 0) {
            discount += n % 10;
            n /= 10;
        }
        p.setDiscount(discount);

        oos.writeObject(p);
        oos.flush();

        socket.close();
    }
}