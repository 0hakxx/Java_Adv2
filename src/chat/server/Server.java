package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.MyLogger.log; // MyLogger 유틸리티에서 log 메서드를 정적 임포트

public class Server {
    private final int port; // 서버가 리스닝할 포트 번호
    private final CommandManager commandManager; // 클라이언트 명령을 처리하는 매니저
    private final SessionManager sessionManager; // 모든 활성 세션을 관리하는 매니저

    private ServerSocket serverSocket; // 서버 소켓 인스턴스

    public Server(int port, CommandManager commandManager, SessionManager sessionManager) {
        this.port = port;
        this.commandManager = commandManager;
        this.sessionManager = sessionManager;
    }

    public void start() throws IOException {
        log("서버 시작: " + commandManager.getClass().getSimpleName()); // 서버 시작 로그 (CommandManager 클래스 이름 포함)
        serverSocket = new ServerSocket(port); // 지정된 포트로 ServerSocket 생성
        log("서버 소켓 시작 - 리스닝 포트: " + port); // 서버 소켓 리스닝 시작 로그

        addShutdownHook(); // JVM 종료 시 서버 자원을 정리하기 위한 셧다운 훅 추가
        running(); // 서버가 계속해서 클라이언트 연결을 수락하도록 하는 메서드 호출
    }

    // JVM 종료 시 실행될 셧다운 훅을 등록
    private void addShutdownHook() {
        // ShutdownHook 인스턴스 생성, 서버 소켓과 세션 매니저를 인자로 전달
        ShutdownHook target = new ShutdownHook(serverSocket, sessionManager);
        // 새로운 스레드에 ShutdownHook을 랩핑하여 런타임에 셧다운 훅으로 추가
        Runtime.getRuntime().addShutdownHook(new Thread(target, "shutdown"));
    }

    private void running() {
        try {
            while (true) { // 무한 루프를 통해 지속적으로 클라이언트 연결을 수락
                Socket socket = serverSocket.accept(); // 클라이언트 연결을 기다리며 블로킹. 연결이 수락되면 Socket 객체 반환
                log("소캣 연결: " + socket); // 새 클라이언트 소켓 연결 로그

                // 새 클라이언트 소켓, CommandManager, SessionManager를 사용하여 Session 객체 생성
                Session session = new Session(socket, commandManager, sessionManager);
                Thread thread = new Thread(session); // Session을 새 스레드에서 실행
                thread.start(); // 스레드 시작
            }
        } catch (IOException e) {
            // 서버 소켓이 닫히거나 입출력 오류가 발생할 경우 예외 처리
            log("서버 소캣 종료: " + e); // 서버 소켓 종료 로그
        }
    }

    static class ShutdownHook implements Runnable {
        private final ServerSocket serverSocket; // 닫을 서버 소켓
        private final SessionManager sessionManager; // 관리 중인 세션을 닫을 세션 매니저

        public ShutdownHook(ServerSocket serverSocket, SessionManager sessionManager) {
            this.serverSocket = serverSocket;
            this.sessionManager = sessionManager;
        }

        @Override
        public void run() {
            log("shutdownHook 실행");
            try {
                sessionManager.closeAll(); // 모든 활성 세션 닫기
                serverSocket.close(); // 서버 소켓 닫기

                Thread.sleep(1000); // 자원 정리를 위한 1초 대기
            } catch (Exception e) {
                e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
                System.out.println("e = " + e); // 예외 메시지 출력
            }
        }
    }
}