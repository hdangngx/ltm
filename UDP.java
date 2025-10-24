
package udp;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class UDP {
    public static void main(String[] args) throws Exception {
        String host = "203.162.10.109";
        int port = 2207;
        String msv = "B22DCVT138";
        String qCode = "wMlnq1se";

        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(5000); // 5s theo đề

        // a) Gửi ";studentCode;qCode"
        String first = ";" + msv + ";" + qCode;
        byte[] out = first.getBytes(StandardCharsets.UTF_8);
        socket.send(new DatagramPacket(out, out.length, InetAddress.getByName(host), port));

        // b) Nhận "requestId;string"
        byte[] buf = new byte[8192];
        DatagramPacket pkt = new DatagramPacket(buf, buf.length);
        socket.receive(pkt);
        String received = new String(pkt.getData(), 0, pkt.getLength(), StandardCharsets.UTF_8).trim();

        int p = received.indexOf(';');
        String requestId = received.substring(0, p);
        String payload = received.substring(p + 1); // "token:idx,token:idx,..."

        // c) Parse -> sort theo idx tăng dần -> ghép lại danh sách token bằng dấu ','
        String[] pairs = payload.split(",");
        List<String[]> items = new ArrayList<>(pairs.length);
        for (String s : pairs) {
            int c = s.lastIndexOf(':');
            items.add(new String[]{ s.substring(0, c), s.substring(c + 1) });
        }
        items.sort(Comparator.comparingInt(a -> Integer.parseInt(a[1])));

        StringBuilder sorted = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) sorted.append(',');
            sorted.append(items.get(i)[0]);
        }

        // Gửi lại "requestId;sorted"
        String answer = requestId + ";" + sorted;
        byte[] ans = answer.getBytes(StandardCharsets.UTF_8);
        socket.send(new DatagramPacket(ans, ans.length, pkt.getAddress(), pkt.getPort()));

        // d) Đóng socket
        socket.close();
    }
}
