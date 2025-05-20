package io.text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static io.text.TextConst.*; // 파일 이름이 정의된 상수를 불러오기 위함
import static java.nio.charset.StandardCharsets.*; // UTF-8 등 표준 문자셋 사용을 위한 static import

public class ReaderWriterMainV1 {



    public static void main(String[] args) throws IOException {
        // 파일에 저장할 문자열
        String writeString = "ABC";

        // 문자열 "ABC" 을 UTF-8 인코딩을 이용해 바이트 배열로 변환
        byte[] writeBytes = writeString.getBytes(UTF_8);

        // 변환된 바이트 배열을 콘솔에 출력
        System.out.println("write String: " + writeString);
        System.out.println("write bytes: " + Arrays.toString(writeBytes));

        // 바이트 배열을 파일에 기록
        FileOutputStream fos = new FileOutputStream(FILE_NAME); // FILE_NAME은 TextConst에 정의된 파일 경로
        fos.write(writeBytes); // 바이트 배열 쓰기
        fos.close(); // 파일 스트림 닫기

        // 파일에서 바이트를 모두 읽어오기
        FileInputStream fis = new FileInputStream(FILE_NAME); // 같은 파일 열기
        byte[] readBytes = fis.readAllBytes(); // 파일의 모든 바이트 읽기
        fis.close(); // 파일 스트림 닫기

        // 읽은 바이트 배열을 UTF-8로 디코딩하여 문자열로 변환
        String readString = new String(readBytes, UTF_8);

        // 읽은 바이트 배열과 복원된 문자열을 출력
        System.out.println("read bytes: " + Arrays.toString(readBytes));
        System.out.println("read String: " + readString);
    }
}
