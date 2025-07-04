package chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static network.tcp.SocketCloseUtil.*; // 소켓, 스트림 닫기 유틸리티 메서드를 정적 임포트
import static util.MyLogger.log; // MyLogger 유틸리티에서 log 메서드를 정적 임포트

public class Session implements Runnable {

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final CommandManager commandManager;
    private final SessionManager sessionManager;

    private boolean closed = false;
    private String username;

    public Session(Socket socket, CommandManager commandManager, SessionManager sessionManager) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.commandManager = commandManager;
        this.sessionManager = sessionManager;
        this.sessionManager.add(this);
    }

    @Override
    public void run() {
        try {
            while (true) { // 클라이언트로부터 메시지를 지속적으로 읽기 위한 무한 루프
                // 클라이언트로부터 문자열 메시지를 읽음 (블로킹)
                String received = input.readUTF();
                log("client -> server: " + received); // 받은 메시지 로그

                // ✅받은 메시지를 어떻게 처리할 것인지 구현하기 위해 별도 CommandManager 클래스를 통해 처리
                commandManager.execute(received, this);
            }
        } catch (IOException e) {
            // 클라이언트 연결이 끊기거나 읽기 오류가 발생할 경우 예외 처리
            log(e); // 예외 메시지 로그
        } finally {
            sessionManager.remove(this); // 현재 세션을 SessionManager에서 제거
            // 다른 모든 클라이언트에게 퇴장 메시지 전송 (사용자 이름이 설정된 경우)
            if (username != null) {
                sessionManager.sendAll(username + "님이 퇴장했습니다.");
            }
            close(); // 세션 자원 정리
        }
    }

    public void send(String message) throws IOException {
        log("server -> client: " + message); // 보낼 메시지 로그
        output.writeUTF(message); // 문자열 메시지를 클라이언트로 전송
    }

    public void close() {
        if (closed) { // 이미 닫힌 경우 바로 반환
            return;
        }
        closeAll(socket, input, output);
        closed = true; // 닫힘 플래그 설정
        log("연결 종료: " + socket);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}