package network.tcp.v1;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static util.MyLogger.log; // 커스텀 로거 클래스인 MyLogger의 log 메서드를 정적으로 임포트하여 클래스 이름 없이 바로 사용할 수 있게 합니다.


public class ClientV1 {

    // 서버가 통신을 위해 대기하는 포트 번호를 상수로 정의합니다.
    private static final int PORT = 12345;


    public static void main(String[] args) throws IOException {
        log("클라이언트 시작"); // 클라이언트 애플리케이션 시작을 로그에 기록합니다.

        // Step 1: 서버와의 연결을 시도합니다.
        // "localhost"는 현재 실행 중인 컴퓨터를 의미하며, 지정된 PORT(12345)로 서버에 연결을 요청합니다.
        // 이 시점에서 서버가 해당 포트에서 대기하고 있지 않으면 ConnectionRefusedException이 발생할 수 있습니다.
        Socket socket = new Socket("localhost", PORT);

        // Step 2: 소켓으로부터 입력 스트림(데이터를 읽기 위한)과 출력 스트림(데이터를 쓰기 위한)을 얻습니다.
        // DataInputStream과 DataOutputStream을 사용하여 기본 데이터 타입(여기서는 UTF 문자열)을 편리하게 처리할 수 있습니다.
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        log("소켓 연결: " + socket); // 성공적인 소켓 연결 정보를 로그에 기록합니다. (소켓 객체의 toString() 결과 출력)

        // Step 3: 서버에게 문자열 메시지를 보냅니다.
        String toSend = "Hello";
        output.writeUTF(toSend); // 문자열을 UTF-8 형식으로 변환하여 출력 스트림에 씁니다.
        log("client -> server: " + toSend); // 보낸 메시지를 로그에 기록합니다.

        // Step 4: 서버로부터 응답 문자열 메시지를 받습니다.
        String received = input.readUTF(); // 입력 스트림으로부터 UTF-8 형식의 문자열을 읽어옵니다.
        log("client <- server: " + received); // 받은 메시지를 로그에 기록합니다.

        // Step 5: 통신이 완료된 후 자원(스트림 및 소켓)을 정리합니다.
        // 자원 누수를 방지하고, 운영체제 리소스를 해제하기 위해 반드시 닫아주어야 합니다.
        log("연결 종료: " + socket); // 연결 종료를 로그에 기록합니다.
        input.close();  // 입력 스트림 닫기
        output.close(); // 출력 스트림 닫기
        socket.close(); // 소켓 닫기
    }
}