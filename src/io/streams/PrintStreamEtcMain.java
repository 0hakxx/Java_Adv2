package io.streams;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class PrintStreamEtcMain {

    public static void main(String[] args) throws FileNotFoundException { // 파일이 없을 경우 발생할 수 있는 FileNotFoundException을 호출 스택으로 던집니다.
        // FileOutputStream 객체를 생성하여 "temp/print.txt" 파일에 바이트 스트림으로 데이터를 쓸 준비를 합니다.
        // "temp" 디렉토리가 없으면 FileNotFoundException이 발생할 수 있습니다.
        FileOutputStream fos = new FileOutputStream("temp/print.txt");


        // System.out.println()과 System.out.printf()에서 사용하는 System.out도 PrintStream 타입입니다.
        PrintStream printStream = new PrintStream(fos); //System.out에 쓰는 스트림

        // printStream의 println() 메소드를 사용하여 문자열 "hello java!"를 파일에 쓰고 줄바꿈합니다.
        printStream.println("hello java!");
        // printStream의 println() 메소드를 사용하여 정수 10을 문자열로 변환하여 파일에 쓰고 줄바꿈합니다.
        printStream.println(10);
        // printStream의 println() 메소드를 사용하여 boolean 값 true를 문자열로 변환하여 파일에 쓰고 줄바꿈합니다.
        printStream.println(true);
        // printStream의 printf() 메소드를 사용하여 형식 문자열에 맞춰 데이터를 포맷하여 파일에 출력합니다.
        // 여기서 "%s"는 문자열 인자("world")가 들어갈 자리임을 나타냅니다. 줄바꿈은 포함되지 않습니다.
        printStream.printf("hello %s", "world");
        // PrintStream을 닫습니다. 이는 내부적으로 래핑된 FileOutputStream도 닫고,
        // 버퍼에 남아있는 모든 데이터를 파일에 플러시(flush)하며 시스템 자원을 해제합니다.
        printStream.close();
    }
}