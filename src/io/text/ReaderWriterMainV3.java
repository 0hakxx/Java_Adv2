package io.text;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets; // StandardCharsets는 V3에서 직접 사용되지는 않지만, V2와의 비교를 위해 남겨둘 수 있습니다.

import static io.text.TextConst.*; // FILE_NAME 상수를 사용하기 위한 import
import static java.nio.charset.StandardCharsets.*; // UTF_8 인코딩 상수를 사용하기 위한 import. FileReader/Writer는 직접 Charset 객체를 받지만, StandardCharsets 클래스를 통해 접근합니다.

public class ReaderWriterMainV3 {

    public static void main(String[] args) throws IOException {
        String writeString = "ABC";
        System.out.println("write String: " + writeString);

        // ========= 파일에 쓰기 =========
        // V2에서는 FileOutputStream과 OutputStreamWriter를 조합하여 바이트 스트림을 문자 스트림으로 변환했지만,
        // V3에서는 FileWriter를 직접 사용하여 파일에 문자를 쓸 수 있습니다.
        // FileWriter는 내부적으로 FileOutputStream과 OutputStreamWriter의 기능을 포함하고 있습니다.
        // 두 번째 인자로 Charset 객체(예: UTF_8)를 받아 인코딩 방식을 지정할 수 있습니다.
        FileWriter fw = new FileWriter(FILE_NAME, UTF_8);
        fw.write(writeString); // 문자열을 파일에 쓰기
        fw.close(); // 스트림 닫기

        // ========= 파일에서 읽기 =========
        StringBuilder content = new StringBuilder();
        // V2에서는 FileInputStream과 InputStreamReader를 조합하여 바이트 스트림을 문자 스트림으로 변환했지만,
        // V3에서는 FileReader를 직접 사용하여 파일에서 문자를 읽을 수 있습니다.
        // FileReader는 내부적으로 FileInputStream과 InputStreamReader의 기능을 포함하고 있습니다.
        // 두 번째 인자로 Charset 객체(예: UTF_8)를 받아 디코딩 방식을 지정할 수 있습니다.
        FileReader fr = new FileReader(FILE_NAME, UTF_8);
        int ch;
        // V2와 마찬가지로 한 문자(char)씩 반복해서 읽어 StringBuilder에 누적합니다.
        while ((ch = fr.read()) != -1) {
            content.append((char) ch);
        }
        fr.close(); // 스트림 닫기

        System.out.println("read String: " + content);
    }
}