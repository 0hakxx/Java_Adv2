package network.tcp.v6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static network.tcp.SocketCloseUtil.*; // 소켓 자원 해제를 위한 유틸리티 클래스 임포트
import static util.MyLogger.log;

public class SessionV6 implements Runnable { // Runnable 인터페이스를 구현하여 별도의 스레드에서 실행.

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final SessionManagerV6 sessionManager;
    private boolean closed = false;

    public SessionV6(Socket socket, SessionManagerV6 sessionManager) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.sessionManager = sessionManager;
        // 세션이 생성될 때 세션 매니저에 자신을 등록하여 관리
        this.sessionManager.add(this);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String received = input.readUTF();
                log("client -> server: " + received);

                if (received.equals("exit")) {
                    break;
                }

                String toSend = received + " World!";
                output.writeUTF(toSend);
                log("client <- server: " + toSend);
            }
        } catch (IOException e) {
            // 클라이언트 연결 끊길 시 로그 출력하고 finally에서 remove로 세션 제거
            log(e);
        } finally {
            sessionManager.remove(this); // 정상 종료, 예외 처리 모두  SessionManager에서 자신을 제거한다.
            close(); // 세션의 자원(소켓, 스트림)을 닫는 메서드 호출
        }
    }
    /**
     * 세션에서 사용하는 자원(socket, input, output)을 안전하게 닫는 메서드
     * ✅ try-with-resources를 사용하지 않는 이유:
     *    - try-with-resources를 쓰면 자원이 자동으로 닫히기 때문에 편리
     *    - 하지만 `SessionManager`가 서버 종료 시 모든 세션의 자원을 직접 닫는 경우도 있으므로 `close()` 메서드를 따로 생성하여 같은 메서드를 쓰기 위함.
     * ✅ 즉, 두 곳에서 호출될 수 있기 때문이다.
     *    1. 클라이언트가 연결을 종료한 경우 → finally 블록에서 호출
     *    2. 서버 전체가 종료되는 경우 → SessionManager.clossAll() 에서 모든 세션을 순회하며 호출
     * ✅ 세션과 서버가 동시에 만약 종료되면 close()가 두 번 호출될 수 있으므로
     *    syncronized를 사용해서 순서대로 처리하게 하고 거기에 또한  if(closed) 으로 해서 한 번 호출하면 다시 호출안되게 처리
     */
    public synchronized void close() {
        if (closed) return;  // 자원이 이미 닫혔으면 아무 작업도 하지 않음 (중복 호출 방지)
        closeAll(socket, input, output);
        closed = true;
        log("연결 종료: " + socket);
    }
}