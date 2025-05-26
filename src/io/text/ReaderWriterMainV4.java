package io.text;

import java.io.*; // java.io 패키지의 모든 클래스(Reader, Writer, BufferedReader, BufferedWriter 등)를 임포트합니다.

import static io.text.TextConst.FILE_NAME; // FILE_NAME 상수를 사용하기 위한 import (파일 경로)
import static java.nio.charset.StandardCharsets.UTF_8; // UTF_8 인코딩 상수를 사용하기 위한 import

public class ReaderWriterMainV4 {

    // V3과의 차이점: 버퍼링된 I/O를 위해 버퍼 크기를 상수로 정의했습니다.
    private static final int BUFFER_SIZE = 8192; // 8KB 버퍼

    public static void main(String[] args) throws IOException {
        // V3과의 차이점: 한글('가나다')과 줄바꿈 문자('\n')를 포함한 문자열을 사용합니다.
        // 이는 BufferedReader의 readLine() 메소드 기능을 테스트하기 위함입니다.
        String writeString = "ABC\n가나다";
        System.out.println("== Write String ==");
        System.out.println(writeString);

        // ========= 파일에 쓰기 =========
        FileWriter fw = new FileWriter(FILE_NAME, UTF_8);

        // V3과의 주요 차이점 (쓰기): FileWriter를 BufferedWriter로 래핑하여 버퍼링 기능을 추가했습니다.
        // 작은 단위의 write 호출이 많을 때 실제 디스크 I/O는 버퍼가 찰 때까지 지연됩니다.
        //BuffredWriter의 부모는 Writer이다.
        BufferedWriter bw = new BufferedWriter(fw, BUFFER_SIZE);
        bw.write(writeString); // 버퍼에 문자열을 씁니다.
        bw.close();

        // ========= 파일에서 읽기 =========
        StringBuilder content = new StringBuilder(); // 읽은 내용을 저장할 StringBuilder 객체

        // V3과 동일하게 FileReader를 사용하여 파일에서 문자를 읽을 기본 스트림을 생성합니다.
        FileReader fr = new FileReader(FILE_NAME, UTF_8);
        // V3과의 주요 차이점 (읽기): FileReader를 BufferedReader로 래핑하여 버퍼링 기능을 추가했습니다.
        // BufferedReader는 내부 버퍼를 사용하여 read 호출 횟수를 줄여 I/O 성능을 향상시킵니다.
        // V3에서 FileReader의 read()는 한 문자씩 읽었지만, BufferedReader는 더 큰 단위로 읽어옵니다.
        BufferedReader br = new BufferedReader(fr, BUFFER_SIZE);

        String line;
        // V3과의 가장 큰 차이점 (읽기 방식): read() 메소드 대신 readLine() 메소드를 사용합니다.
        // readLine()은 파일에서 한 줄(줄바꿈 문자를 포함하지 않는)을 읽어 String으로 반환하며,
        // 파일의 끝(EOF)에 도달하면 반환타입이 String이기 때문에 -1이 아닌, null을 반환합니다.
        while ((line = br.readLine()) != null) {
            // V3은 read()로 한 문자씩 읽어왔지만, V4는 readLine()으로 한 줄씩 읽어옵니다.
            // readLine()은 줄바꿈 문자를 제거하고 반환하므로, StringBuilder에 다시 '\n'을 추가하여
            // 원본 파일의 줄바꿈 구조를 유지합니다.
            content.append(line).append("\n");
        }
        br.close(); // 스트림 닫기

        System.out.println("== Read String ==");
        // V3과 마찬가지로 StringBuilder에 저장된 내용을 출력합니다.
        System.out.println(content);
    }
}