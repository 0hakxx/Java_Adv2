package io.file.text;

import java.io.IOException;
import java.nio.charset.StandardCharsets; // 문자 인코딩을 위한 StandardCharsets 클래스 임포트
import java.nio.file.Files; // 파일 작업을 위한 Files 유틸리티 클래스 임포트
import java.nio.file.Path; // 파일 또는 디렉토리 경로를 나타내는 Path 인터페이스 임포트

import static java.nio.charset.StandardCharsets.*; // StandardCharsets의 상수들(예: UTF_8)을 직접 사용하기 위해 static 임포트

public class ReadTextFileV1 {

    // 파일을 저장할 경로를 상수로 정의
    private static final String PATH = "temp/hello2.txt";

    public static void main(String[] args) throws IOException {
        // 파일에 쓸 문자열을 정의합니다.
        // "abc\n가나다"는 "abc" 다음에 줄바꿈, 그리고 "가나다"가 이어지는 문자열
        String writeString = "abc\n가나다";
        System.out.println("== Write String ==");
        System.out.println(writeString);

        // 문자열 PATH를 Path 객체로 변환합니다.
        // Path 객체는 파일 시스템 경로를 추상화하며, 다양한 파일 작업에 사용됩니다.
        Path path = Path.of(PATH);

        // 파일에 쓰기:
        // Files.writeString() 메서드는 자바 7(NIO.2)부터 도입된 Files 클래스의 정적 메서드입니다.
        // 이 메서드가 파일을 간단하게 쓸 수 있는 이유는 다음과 같습니다:
        // 1. 내부적으로 스트림(Stream) 관리: 파일을 열고, 쓰고, 닫는 모든 과정을 자동으로 처리합니다.
        //    개발자가 직접 OutputStream이나 BufferedWriter를 열고 닫는 복잡한 작업을 할 필요가 없습니다.
        // 2. 바이트 변환 자동 처리: 주어진 String과 Charset(여기서는 UTF_8)을 사용하여 문자열을
        //    적절한 바이트로 변환하여 파일에 씁니다. 인코딩/디코딩에 대한 신경을 덜 수 있습니다.
        // 3. 간결한 API: 단 한 줄의 코드로 파일 쓰기 작업을 완료할 수 있어 코드가 매우 간결해집니다.
        //    파일이 없으면 새로 생성하고, 이미 존재하면 덮어씁니다.
        Files.writeString(path, writeString, UTF_8);

        // 파일에서 읽기:
        // Files.readString() 메서드도 Files 클래스의 정적 메서드로, 파일의 모든 내용을 읽어 String으로 반환합니다.
        // 1. 내부적으로 스트림(Stream) 관리: 파일을 열고, 읽고, 닫는 모든 과정을 자동으로 처리합니다.
        // 2. 바이트 변환 자동 처리: 파일에서 읽은 바이트를 주어진 Charset(여기서는 UTF_8)을 사용하여
        //    문자열로 정확히 디코딩하여 반환합니다.
        // 3. 간결한 API: 마찬가지로 단 한 줄의 코드로 파일 읽기 작업을 완료할 수 있어 매우 편리합니다.
        String readString = Files.readString(path, UTF_8);

        System.out.println("== Read String ==");
        System.out.println(readString);
    }
}