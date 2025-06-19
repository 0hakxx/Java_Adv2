package network.tcp.v6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log;

public class ServerV6 {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        log("서버 시작");
        // 클라이언트 세션들을 관리하는 SessionManagerV6 객체 생성. 서버 종료 시 모든 클라이언트 소켓을 안전하게 닫기 위함이다.
        SessionManagerV6 sessionManager = new SessionManagerV6();
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("서버 소켓 시작 - 리스닝 포트: " + PORT);

        // JVM 종료 시(Ctrl+C 등) 자원(서버 소켓, 클라이언트 소켓)을 안전하게 반납하기 위한 ShutdownHook 등록
        ShutdownHook shutdownHook = new ShutdownHook(serverSocket, sessionManager);
        Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook, "shutdown"));

        try {
            while (true) {
                Socket socket = serverSocket.accept(); // 블로킹
                log("소켓 연결: " + socket);

                // 새로운 클라이언트 연결마다 SessionV6 객체를 생성하여 해당 클라이언트와의 통신을 처리.
                SessionV6 session = new SessionV6(socket, sessionManager);
                Thread thread = new Thread(session);
                thread.start();
            }
        } catch (IOException e) {
            // 서버 소켓이 닫히거나 예외 발생 시 로그 출력.
            // 특히 ShutdownHook에 의해 serverSocket.close()가 호출되면 여기서 예외가 발생하며 서버 루프가 종료된다.
            log("서버 소켓 종료: " + e);
        }
    }

    // JVM 종료 시 호출되는 스레드. 서버의 모든 자원을 안전하게 해제한다.
    static class ShutdownHook implements Runnable {
        private final ServerSocket serverSocket;
        private final SessionManagerV6 sessionManager;

        public ShutdownHook(ServerSocket serverSocket, SessionManagerV6 sessionManager) {
            this.serverSocket = serverSocket;
            this.sessionManager = sessionManager;
        }

        @Override
        public void run() {
            log("shutdownHook 실행");
            try {
                sessionManager.closeAll(); // SessionManager를 통해 모든 활성 클라이언트 세션의 소켓을 닫음.
                serverSocket.close();      // 서버 소켓을 닫아 더 이상 새로운 클라이언트 연결을 받지 않음.
                Thread.sleep(1000);  // 모든 자원이 완전히 정리될 시간을 주기 위해 잠시 대기
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("e = " + e);
            }
        }
    }
}