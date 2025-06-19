package io.start;

import java.io.IOException;
import java.io.PrintStream;

import static java.nio.charset.StandardCharsets.*; // UTF_8 같은 상수를 편하게 쓰기 위해 정적 임포트

public class PrintStreamMain {

    public static void main(String[] args) throws IOException {
        PrintStream printStream = System.out;

        byte[] bytes = "Hello!\n".getBytes(UTF_8);
        printStream.write(bytes);

        // 우리가 자주 사용했던 System.out 이 사실은 PrintStream 이다.
        printStream.println("Print!");  //System.out.println("Print!")
    }
}
