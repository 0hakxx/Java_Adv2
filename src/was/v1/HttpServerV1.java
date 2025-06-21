package was.v1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.*;
import static util.MyLogger.log;
/*
✅ 왜 DataInputStream, DataOutputStream을 안 쓰고 BufferedReader, PrintWriter를 쓰는 이유
💬 HTTP는 줄(line) 단위로 동작하는 "텍스트 프로토콜"
⚠️ 그런데 DataInputStream.readUTF()는?
- 줄 단위가 아니라 Java끼리만 이해 가능한 바이너리 포맷(HTTP랑 형식이 다름)
- 사용 목적 : Java 프로그램 간 통신 (직렬화 기반) => 안드로이드(Java)에서 많이 사용
*/
public class HttpServerV1 {

    private final int port;

    public HttpServerV1(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        log("서버 시작 port: " + port);

        while (true) { // 클라이언트가 접속할 때마다 accept()로 처리
            Socket socket = serverSocket.accept(); // 블로킹: 클라이언트가 연결 요청할 때까지 대기
            process(socket); // 연결이 수립되면 요청을 처리하는 메서드로 전달
        }
    }

    private void process(Socket socket) throws IOException { // HTTP 요청을 처리하는 메서드
        try (socket;
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), UTF_8));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), false, UTF_8)) {

            // 요청 메시지를 문자열로 파싱
            String requestString = requestToString(reader);

            // 브라우저가 자동으로 보내는 favicon 요청은 로깅만 하고 무시
            if (requestString.contains("/favicon.ico")) {
                log("favicon 요청");
                return;
            }

            // 요청 전체 로그 출력
            log("HTTP 요청 정보 출력");
            System.out.println(requestString);

            log("HTTP 응답 생성중...");
            /*
            ⚠️ 중요: 현재 서버는 단일 스레드 방식으로 순차 처리하여 동작
            만약 2개의 브라우저 접속 시 첫 번째 브라우저는 5초, 두 번째 브라우저는 10초 지연 발생함!!
            */
            sleep(5000); // 서버 처리 시간 대기

            // 클라이언트에게 응답 전송
            responseToClient(writer);
            log("HTTP 응답 전달 완료");
        }
    }

    // HTTP 요청 메시지를 문자열로 변환하는 메서드 (헤더만 읽음)
    private static String requestToString(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) { // 빈 줄을 만나면 요청 헤더 종료 (HTTP 규칙)
                break;
            }
            sb.append(line).append("\n"); // 헤더 줄 추가
        }
        return sb.toString(); // 헤더만 반환
    }

    // HTTP 응답 메시지를 생성하고 클라이언트로 전송
        private void responseToClient(PrintWriter writer) {
            String body = "<h1>Hello World</h1>"; // 웹 브라우저에 전달하는 HTML 본문 (Body)
            // Content-Length 계산 (바이트 수 기준, 한글 포함 시 UTF-8 고려)
            int length = body.getBytes(UTF_8).length;
            // HTTP 응답 메시지 생성 (헤더 + 바디)
            StringBuilder sb = new StringBuilder();
            sb.append("HTTP/1.1 200 OK\r\n"); // 응답 상태줄
            sb.append("Content-Type: text/html\r\n"); // 응답 본문의 타입 명시
            sb.append("Content-Length: ").append(length).append("\r\n"); // 바디의 길이 지정 (필수 헤더)
            sb.append("\r\n"); // 헤더와 바디를 구분하는 빈 줄
        sb.append(body); // HTML 바디

        // 로그 출력
        log("HTTP 응답 정보 출력");
        System.out.println(sb);

        // 응답 전송
        writer.println(sb); // println은 자동으로 \r\n을 추가함
        writer.flush();     // 버퍼를 비워 클라이언트에게 실제 전송
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
