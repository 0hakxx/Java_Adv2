package network.tcp.v3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log;

public class ServerV3 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트: " + PORT);

        // --- ServerV2와 ServerV3의 주요 차이점 ---
        // V2는 serverSocket.accept()를 한 번만 호출하여 단일 클라이언트와의 통신을 처리했지만,
        // V3는 while(true) 루프 안에서 serverSocket.accept()를 계속 호출합니다.
        // 이는 여러 클라이언트의 동시 접속 요청을 '지속적으로' 받아들일 수 있음을 의미합니다.

        // 하지만 V2와 같이 메인 스레드에서 바로 통신을 처리하면,
        // 하나의 클라이언트와 통신하는 동안 다른 클라이언트의 연결 요청을 처리하지 못하게 됩니다.
        // 이를 해결하기 위해 V3에서는 새로운 클라이언트가 연결될 때마다 '별도의 스레드'를 생성하여
        // 해당 클라이언트와의 통신을 전담하게 합니다.
        // ----------------------------------------

        while (true) { // 무한 루프: 계속해서 클라이언트 연결을 기다리고 수락합니다.
            Socket socket = serverSocket.accept(); // 클라이언트 연결을 기다리고 수락합니다. (블로킹)
            log("소켓 연결: " + socket);

            // SessionV3 클래스는 Runnable 인터페이스를 구현하여 스레드에서 실행될 수 있도록 합니다.
            // 이 SessionV3 객체는 특정 클라이언트(socket)와의 통신 로직을 캡슐화합니다.
            SessionV3 session = new SessionV3(socket);

            // 새로운 스레드를 생성하여 SessionV3 객체(클라이언트 통신 로직)를 실행합니다.
            // 이렇게 함으로써 메인 스레드는 다음 클라이언트 연결 요청을 기다리는 `serverSocket.accept()`로 즉시 돌아갈 수 있고,
            // 이전 클라이언트와의 통신은 새로 생성된 스레드가 백그라운드에서 독립적으로 처리합니다.
            // 이것이 V2의 단일 클라이언트 처리 한계를 극복하고 '다중 클라이언트'를 처리할 수 있게 하는 핵심입니다.
            Thread thread = new Thread(session);
            thread.start(); // 스레드를 시작하여 SessionV3의 run() 메서드(클라이언트 통신)를 실행합니다.
        }
    }
}