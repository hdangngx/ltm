package tcpdatastream;
import java.io.*;
import java.net.*;

public class TCPDataStream {
    public static void main(String[] args) throws Exception {
        String serverHost = "203.162.10.109"; 
        int serverPort = 2207;                
        String studentCode = "B22DCVT138";    
        String qCode = "R4mEQIhl";            

        Socket socket = new Socket(serverHost, serverPort);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // a) Gửi "studentCode;qCode"
        dos.writeUTF(studentCode + ";" + qCode);
        dos.flush();

        // b) Nhận lần lượt: chuỗi mã hóa (cipher) và độ dịch s
        String cipher = dis.readUTF();
        int s = dis.readInt();

        // c) Giải mã Caesar và gửi lại
        String plain = caesarDecrypt(cipher, s);
        dos.writeUTF(plain);
        dos.flush();

        // d) Đóng kết nối
        socket.close();
    }

    // Giải mã Caesar: dịch ngược s, giữ nguyên ký tự không phải chữ cái
    private static String caesarDecrypt(String text, int s) {
        StringBuilder sb = new StringBuilder();
        int shift = ((s % 26) + 26) % 26; // chuẩn hóa s về [0..25]
        for (char ch : text.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                sb.append((char) ('A' + (ch - 'A' - shift + 26) % 26));
            } else if (ch >= 'a' && ch <= 'z') {
                sb.append((char) ('a' + (ch - 'a' - shift + 26) % 26));
            } else {
                sb.append(ch); // số, khoảng trắng, ký tự khác giữ nguyên
            }
        }
        return sb.toString();
    }
}