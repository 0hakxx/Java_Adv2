package io.text;

import java.io.*;

import static io.text.TextConst.*; // FILE_NAME 상수를 사용하기 위한 import
import static java.nio.charset.StandardCharsets.*; // UTF_8 인코딩 상수를 사용하기 위한 import

public class ReaderWriterMainV2 {

    public static void main(String[] args) throws IOException {
        // 파일에 쓸 문자열
        String writeString = "abc";
        System.out.println("write String: " + writeString);

        // ========= 파일에 쓰기 =========
        FileOutputStream fos = new FileOutputStream(FILE_NAME);

        // V1과의 차이점: V1에서는 FileOutputStream에 직접 byte[]를 썼지만,
        // V2에서는 OutputStreamWriter를 통해 문자열을 직접 쓸 수 있음 (문자 스트림 사용)
        OutputStreamWriter osw = new OutputStreamWriter(fos, UTF_8); // 문자 → 바이트로 인코딩 (UTF-8)
        osw.write(writeString); // 문자열을 파일에 쓰기
        osw.close(); // 스트림 닫기

        // ========= 파일에서 읽기 =========
        FileInputStream fis = new FileInputStream(FILE_NAME);
        // V1과의 차이점: V1에서는 FileInputStream의 readAllBytes()로 바이트 배열을 한 번에 읽었음

        InputStreamReader isr = new InputStreamReader(fis, UTF_8);
        // V1과의 차이점: V1에서는 바이트 배열을 읽은 후 new String(bytes, UTF_8)로 디코딩했지만,
        // V2는 InputStreamReader가 자동으로 바이트 → 문자 디코딩을 수행함

        StringBuilder content = new StringBuilder();
        int ch;

        // V1과의 차이점: V1은 한 번에 바이트 배열을 읽었지만,
        // V2는 한 문자(char)씩 반복해서 읽어 StringBuilder에 누적
        while ((ch = isr.read()) != -1) {
            content.append((char) ch);
        }
        isr.close();

        System.out.println("read String: " + content);
    }
}
