package thuchanhlaptrinhmang;
import java.io.*;
import java.net.*;
import java.util.*;

public class Thuchanhlaptrinhmang {
    public static void main(String[] args) throws Exception {
        String serverHost = "203.162.10.109";
        int serverPort = 2208;
        String studentCode = "B22DCVT138";
        String qCode = "V77g2cxh";

        Socket socket = new Socket(serverHost, serverPort);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // a. Gửi mã SV và mã câu hỏi
        bw.write(studentCode + ";" + qCode);
        bw.newLine();
        bw.flush();

        // b. Nhận chuỗi từ server
        String s = br.readLine();

        // c. Đếm ký tự xuất hiện > 1 (chữ hoặc số)
        Map<Character, Integer> count = new LinkedHashMap<>();
        for (char c : s.toCharArray())
            if (Character.isLetterOrDigit(c))
                count.put(c, count.getOrDefault(c, 0) + 1);

        StringBuilder result = new StringBuilder();
        for (var e : count.entrySet())
            if (e.getValue() > 1)
                result.append(e.getKey()).append(":").append(e.getValue()).append(",");

        // d. Gửi kết quả
        bw.write(result.toString());
        bw.newLine();
        bw.flush();

        socket.close();
    }
}