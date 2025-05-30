package io.file.text;

import java.io.IOException; // I/O 작업 중 발생할 수 있는 예외를 처리하기 위한 임포트
import java.nio.charset.StandardCharsets; // 문자 인코딩을 위한 StandardCharsets 클래스 임포트
import java.nio.file.Files; // 파일 및 디렉토리 작업을 위한 Files 유틸리티 클래스 임포트
import java.nio.file.Path; // 파일 또는 디렉토리 경로를 나타내는 Path 인터페이스 임포트
import java.util.List; // 여러 라인을 저장할 List 인터페이스 임포트
import java.util.stream.Stream; // (이 코드에서는 직접 사용되지 않지만, Files 클래스 메서드 중 Stream을 반환하는 경우가 있어 임포트되어 있을 수 있습니다)

import static java.nio.charset.StandardCharsets.UTF_8; // UTF_8 인코딩 상수를 직접 사용하기 위한 static 임포트

public class ReadTextFileV2 {

    // 파일을 저장할 경로를 상수로 정의
    private static final String PATH = "temp/hello2.txt";

    public static void main(String[] args) throws IOException {
        // 파일에 쓸 문자열을 정의합니다. '\n'은 줄바꿈을 의미
        String writeString = "abc\n가나다";
        System.out.println("== Write String ==");
        System.out.println(writeString);

        // 문자열 PATH를 Path 객체로 변환합니다.
        Path path = Path.of(PATH);

        // 파일에 쓰기:
        // Files.writeString() 메서드를 사용하여 지정된 Path에 문자열을 씁니다.
        // 파일이 존재하지 않으면 새로 생성하고, 이미 존재하면 내용을 덮어씁니다.
        // UTF_8 인코딩을 사용하여 문자열이 바이트로 변환됩니다.
        Files.writeString(path, writeString, UTF_8);

        // 파일에서 읽기:
        System.out.println("== Read String ==");
        // Files.readAllLines() 메서드는 지정된 Path의 모든 라인을 읽어서 String 요소의 List로 반환합니다.
        // 각 줄바꿈 문자('\n', '\r\n')를 기준으로 분리된 문자열이 List의 한 요소가 됩니다.
        // 여기서도 UTF_8 인코딩을 사용하여 파일의 바이트를 정확한 문자로 디코딩합니다.
        List<String> lines = Files.readAllLines(path, UTF_8);

        // 읽어온 각 라인을 반복하여 출력합니다.
        // (i + 1)을 사용하여 라인 번호를 1부터 시작하도록 표시합니다.
        for (int i = 0; i < lines.size(); i++) {
            System.out.println((i + 1) + ": " + lines.get(i));
        }


    }
}