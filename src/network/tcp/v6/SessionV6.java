package network.tcp.v6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static network.tcp.SocketCloseUtil.*;
import static util.MyLogger.log;

public class SessionV6 implements Runnable {

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
            log(e);
        } finally {
            sessionManager.remove(this);
            close();
        }
    }

    public synchronized void close() { // 여러 스레드에서 동시에 호출될 경우를 대비해 동기화 (synchronized)
        if (closed) { // 이미 세션이 닫혔으면
            return; // 아무것도 하지 않고 즉시 종료 (중복 호출 방지)
        }
        // SocketCloseUtil.closeAll() 메서드를 사용하여 소켓과 스트림을 안전하게 닫음
        closeAll(socket, input, output); // 실제 자원 해제 로직 (다른 유틸리티에 위임)
        closed = true; // 세션이 닫혔음을 표시
        log("연결 종료: " + socket);
    }
}