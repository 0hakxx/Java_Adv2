package was.v2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.MyLogger.log;

public class HttpServerV2 {

    // 고정 크기 스레드 풀 생성 (최대 10개 스레드까지 동시 처리 가능)
    // 여러 클라이언트의 요청을 병렬로 처리하기 위해 ExecutorService를 사용
    private final ExecutorService es = Executors.newFixedThreadPool(10);

    private final int port;

    public HttpServerV2(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        log("서버 시작 port: " + port);

        while (true) {
            Socket socket = serverSocket.accept();
            es.submit(new HttpRequestHandlerV2(socket));
        }
    }
}
