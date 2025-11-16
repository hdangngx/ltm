
package bai3_tcp_domainedu;

import java.io.*;
import java.net.*;

public class Bai3_TCP_DomainEdu {
    public static void main(String[] args) throws Exception {
        String serverHost  = "203.162.10.109"; // sửa theo Exam_IP
        int    serverPort  = 2208;
        String studentCode = "B22DCVT138";      // sửa lại mã của bạn
        String qCode       = "MeSALZ4K";

        // Kết nối TCP
        Socket socket = new Socket(serverHost, serverPort);
        socket.setSoTimeout(5000);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));

        // a) Gửi "studentCode;qCode"
        writer.write(studentCode + ";" + qCode);
        writer.newLine();
        writer.flush();

        // b) Nhận chuỗi danh sách tên miền
        String data = reader.readLine();

        // c) Lọc các domain .edu
        StringBuilder eduList = new StringBuilder();
        boolean first = true;

        if (data != null && !data.isEmpty()) {
            String[] domains = data.split(",");
            for (String d : domains) {
                String domain = d.trim();
                if (domain.endsWith(".edu")) {
                    if (!first) {
                        eduList.append(", ");
                    }
                    eduList.append(domain);
                    first = false;
                }
            }
        }

        // Gửi lại danh sách .edu
        writer.write(eduList.toString());
        writer.newLine();
        writer.flush();

        // d) Đóng kết nối
        socket.close();
    }
}