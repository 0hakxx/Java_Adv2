package io.streams;

import java.io.*;

// DataOutputStream
// 이 스트림과 FileOutputStream을 조합하면 파일에 자바의 기본 데이터 형(int, double, boolean 등)을 편리하게 저장할 수 있다.

public class DataStreamEtcMain {

    public static void main(String[] args) throws IOException { // main 메소드: 프로그램의 진입점입니다. 입출력 작업 중 발생할 수 있는 IOException을 호출 스택으로 던집니다.
        // FileOutputStream 객체를 생성하여 "temp/data.dat" 파일에 바이트 스트림으로 데이터를 쓸 준비를 합니다.
        // "temp" 디렉토리가 없으면 FileNotFoundException (IOException의 하위 클래스)이 발생할 수 있습니다.
        FileOutputStream fos = new FileOutputStream("temp/data.dat");

        // DataOutputStream 객체를 생성합니다. DataOutputStream은 FileOutputStream과 같은 기본 바이트 스트림을 래핑하여,
        // Java의 기본 데이터 타입(int, double, boolean 등)을 바이너리 형태로 파일에 직접 쓸 수 있는 편리한 메소드들을 제공합니다.
        // 이는 나중에 DataInputStream으로 해당 타입 그대로 읽어들일 때 유용합니다.
        DataOutputStream dos = new DataOutputStream(fos);

        // dos.writeUTF("회원A");
        // UTF-8 형태로 문자열을 파일에 씁니다. 문자열의 길이를 먼저 기록한 후 문자열 바이트를 기록합니다.
        dos.writeUTF("회원A");
        // dos.writeInt(20);
        // 정수 20을 4바이트 바이너리 형태로 파일에 씁니다.
        dos.writeInt(20);
        // dos.writeDouble(10.5);
        // 부동 소수점 수 10.5를 8바이트 바이너리 형태로 파일에 씁니다.
        dos.writeDouble(10.5);
        // dos.writeBoolean(true);
        // boolean 값 true를 1바이트 바이너리 형태로 파일에 씁니다 (true는 1, false는 0).
        dos.writeBoolean(true);
        // DataOutputStream을 닫습니다. 이는 래핑된 FileOutputStream도 함께 닫아 시스템 자원을 해제하고
        // 버퍼에 남아있는 데이터를 파일에 플러시(flush)합니다.
        dos.close();

        // --- 파일에서 데이터 읽기 ---
        // FileInputStream 객체를 생성하여 "temp/data.dat" 파일에서 바이트 스트림으로 데이터를 읽을 준비를 합니다.
        FileInputStream fis = new FileInputStream("temp/data.dat");

        // DataInputStream 객체를 생성합니다. DataInputStream은 FileInputStream과 같은 기본 바이트 스트림을 래핑하여,
        // DataOutputStream으로 기록된 Java 기본 데이터 타입들을 정확히 해당 타입 그대로 읽어들일 수 있는 메소드들을 제공합니다.
        DataInputStream dis = new DataInputStream(fis);

        // dis.readUTF()
        // 파일에서 UTF-8 형식의 문자열을 읽어 반환합니다. 쓸 때와 동일한 순서로 읽어야 합니다.
        System.out.println(dis.readUTF());
        // dis.readInt()
        // 파일에서 4바이트의 정수 값을 읽어 반환합니다.
        System.out.println(dis.readInt());
        // dis.readDouble()
        // 파일에서 8바이트의 부동 소수점 값을 읽어 반환합니다.
        System.out.println(dis.readDouble());
        // dis.readBoolean()
        // 파일에서 1바이트의 boolean 값을 읽어 반환합니다.
        System.out.println(dis.readBoolean());
        // DataInputStream을 닫습니다. 이는 래핑된 FileInputStream도 함께 닫아 시스템 자원을 해제합니다.
        dis.close();
    }
}