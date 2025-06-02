package network.tcp.v1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
// ServerSocket (1개): 서버의 문지기 역할을 합니다. 특정 포트에서 클라이언트의 연결 요청을 기다리고, 연결 요청이 오면 그 요청을 수락(accept)합니다.
// ServerSocket은 오직 연결을 '수락'하는 역할만 합니다.
// Socket (클라이언트 수만큼): 새로 생성된 Socket 객체가 해당 클라이언트와의 개별적인 통신 채널이 됩니다.
// 즉, 100명의 클라이언트가 연결되면 서버는 100개의 Socket 객체를 가지게 되고, 각각의 Socket 객체를 통해 해당 클라이언트와 데이터를 주고받습니다.
// ServerSocket은 '듣기(listening)'와 '연결 수락(accepting)'을 담당합니다.
// Socket은 '실제 데이터 통신(sending/receiving data)'을 담당합니다.



import static util.MyLogger.log;

public class ServerV1 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {

        log("서버 시작");

        // ServerSocket은 서버 애플리케이션에서 사용되며, 클라이언트의 연결 요청을 '대기'하고 '수락'하는 역할을 합니다.
        // 특정 포트(여기서는 12345)를 열고, 해당 포트로 들어오는 클라이언트의 연결을 기다립니다.
        // 마치 특정 상점의 '입구'와 같아서, 손님(클라이언트)이 올 때까지 문을 열어두고 기다리는 역할을 합니다.
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트: " + PORT);

        // serverSocket.accept() 메서드가 클라이언트의 연결 요청을 기다립니다.
        // 이 메서드는 클라이언트의 연결 요청이 들어올 때까지 **블로킹(blocking)** 상태로 대기합니다.
        // 즉, 클라이언트가 연결하기 전까지 다음 코드로 넘어가지 않습니다.
        // accept 함수의 반환값은 Socket 입니다.
        // serverSocket.accept()를 통해 클라이언트의 연결 요청이 수락되면, 해당 클라이언트와 1대1로 통신할 수 있는 새로운 Socket 객체가 생성됩니다.
        // 이는 마치 상점에 손님(클라이언트)이 들어왔을 때, 손님과 상점 주인(서버)이 직접 대화할 수 있는 '상담 창구'가 새로 열리는 것과 같습니다.
        // 이 Socket을 통해 데이터를 주고받게 됩니다.
        Socket socket = serverSocket.accept();
        log("소켓 연결: " + socket);

        // 연결된 소켓에서 데이터를 읽고 쓰기 위한 스트림을 생성합니다.
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        // 클라이언트로부터 UTF 형식의 문자열 메시지를 읽어옵니다.
        String received = input.readUTF();
        // 클라이언트로부터 "Hello" 메시지를 성공적으로 받았음을 로그에 기록합니다.
        log("client -> server: " + received);

        // 받은 메시지에 " World!"를 추가하여 응답 메시지를 생성합니다.
        String toSend = received + " World!";
        // 생성된 응답 메시지를 클라이언트에게 다시 보냅니다.
        output.writeUTF(toSend);
        log("client <- server: " + toSend);

        log("연결 종료: " + socket);
        input.close();
        output.close();
        socket.close(); // 클라이언트와의 통신에 사용된 Socket을 닫습니다.
        serverSocket.close(); // 서버소켓도 닫아서 더 이상 새로운 연결을 받지 않도록 합니다.
    }
}