package chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static network.tcp.SocketCloseUtil.closeAll;
import static util.MyLogger.log;

public class Client {

    private final String host;
    private final int port;

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    private ReadHandler readHandler;
    private WriteHandler writeHandler;
    private boolean closed = false;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException {
        log("클라이언트 시작");
        socket = new Socket(host, port);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        /* ✅중요
        클라이언트는 콘솔의 입력을 받는 것과 서버로부터 오는 메시지를 콘솔에 출력하는 것을 분리해야한다.
        채팅은 실시간으로 대화를 주고받아야 한다.
        사용자의 콘솔 입력이 있을 때까지 무한정 대기하여 실시간으로 다른 사용자가 보낸 메시지를 콘솔에 출력할 수 없기 때문이다.
        ✅Tip
        객체지향 설계 코드 작성 시 스레드를 먼저 작성하면 편리함.
        */
        readHandler = new ReadHandler(input, this);
        writeHandler = new WriteHandler(output, this);
        Thread readThread = new Thread(readHandler, "readHandler");
        Thread writeThread = new Thread(writeHandler, "writeHandler");
        readThread.start();
        writeThread.start();
    }

    public synchronized void close() {
        if (closed) {
            return;
        }
        writeHandler.close();
        readHandler.close();
        closeAll(socket, input, output);
        closed = true;
        log("연결 종료: " + socket);
    }
}