package network.exception.close.normal;


import java.io.*;
import java.net.Socket;

import static util.MyLogger.log;

public class NormalCloseClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        log("소캣 연결: " + socket);

        InputStream input = socket.getInputStream();

        readByInputStream(input, socket);
        readByBufferedReader(input, socket);
        readByDataInputStream(input, socket);

        log("연결 종료: " + socket.isClosed());
    }
    private static void readByInputStream(InputStream input, Socket socket) throws IOException {
        // 서버가 연결을 정상적으로 종료하고 FIN 패킷을 보냈다면, read()는 -1을 반환한다.
        int read = input.read();
        log("read = " + read);

        // read() 메서드가 -1을 반환하면, 서버가 스트림을 정상적으로 닫았음을 의미한다.
        if (read == -1) {
            input.close();
            socket.close();
        }
    }

    private static void readByBufferedReader(InputStream input, Socket socket) throws IOException {
        // InputStream을 InputStreamReader로 감싸 문자 스트림으로 변환하고,
        // BufferedReader로 감싸서 라인 단위 읽기 기능을 제공한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        // 한 라인을 읽는다. 서버가 스트림을 정상적으로 닫고 FIN 패킷을 보냈다면, readLine()은 null을 반환한다.
        String readString = br.readLine();
        log("readString = " + readString);

        // readLine() 메서드가 null을 반환하면, 서버가 스트림을 정상적으로 닫았음을 의미한다.
        if (readString == null) {
            br.close();
            socket.close();
        }
    }

    private static void readByDataInputStream(InputStream input, Socket socket) throws IOException {
        DataInputStream dis = new DataInputStream(input);

        try {
            // 만약 서버가 데이터를 보내기 전에 FIN 패킷을 보냈다면, EOFException이 발생한다.
            dis.readUTF();
        } catch (EOFException e) {
            // EOFException은 "End Of File"을 의미하며, 서버가 FIN 패킷을 보내고 연결을 종료했음을 나타낸다.
            log(e);
        } finally {
            dis.close();
            socket.close();
        }
    }
}