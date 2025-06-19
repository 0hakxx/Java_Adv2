package network.tcp.v2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log;
/*
여기서 궁금한 점은 결국, accept()를 한 번밖에 호출하지 않아서 문제인거라 for 문으로 못 하나? => 불가능
ServerSocket.accept() : 클라이언트가 연결될 때까지 무한정 기다림(블로킹으로 동작)
input.readUTF(): 이 메서드도 클라이언트로부터 데이터가 도착할 때까지 무한정 기다림(블로킹으로 동작)

 for (int i = 0; i < 10; i++) {
        log("클라이언트 연결 기다림...");
        Socket socket = serverSocket.accept(); // <-- 여기서 블로킹!
        log("새 클라이언트 연결: " + socket);
        String received = input.readUTF(); // <-- 여기서 블로킹!
 즉 이런 방식은 직렬 처리: 첫 번째 클라이언트가 연결되고 나면
 서버는 오직  단일 클라이언트와의 통신에 갇혀버린다
*/
public class ServerV2 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트: " + PORT);

        Socket socket = serverSocket.accept();
        log("소켓 연결: " + socket);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        while (true) {
            // 클라이언트로부터 문자 받기
            String received = input.readUTF();
            log("client -> server: " + received);

            if (received.equals("exit")) {
                break;
            }

            // 클라이언트에게 문자 보내기
            String toSend = received + " World!";
            output.writeUTF(toSend);
            log("client <- server: " + toSend);
        }
        // 자원 정리
        log("연결 종료: " + socket);
        input.close();
        output.close();
        socket.close();
        serverSocket.close();
    }
}
