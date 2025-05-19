package io.start;

import java.io.IOException;
import java.io.PrintStream;

import static java.nio.charset.StandardCharsets.*; // UTF_8 같은 상수를 편하게 쓰기 위해 정적 임포트

public class PrintStreamMain {

    public static void main(String[] args) throws IOException {
        // System.out은 자바에서 기본으로 제공하는 콘솔 출력 도구다.
        // 이건 PrintStream 타입인데, 텍스트 출력에 특화된 스트림이다.
        PrintStream printStream = System.out;

        // "Hello!\n"이라는 문자열을 UTF-8 방식으로 바이트 배열로 바꾼다.
        byte[] bytes = "Hello!\n".getBytes(UTF_8);  // \n은 줄바꿈 문자

        // 위에서 만든 바이트 배열을 출력 스트림에 직접 출력한다.
        // printStream은 내부적으로 OutputStream을 상속받았기 때문에 write(byte[]) 사용 가능
        printStream.write(bytes);

        // println은 문자열을 출력하고 자동으로 줄바꿈까지 해준다.
        // 문자열뿐만 아니라 숫자 등도 바로 출력할 수 있다.
        printStream.println("Print!");
    }
}
