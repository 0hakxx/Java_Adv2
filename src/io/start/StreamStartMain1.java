package io.start;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class StreamStartMain1 {

    public static void main(String[] args) throws IOException {
        // FileOutputStream을 생성하여 "temp/hello.dat" 파일을 생성(또는 덮어쓰기)하고,
        // 이 파일에 바이트 데이터를 쓸 준비를 한다.
        FileOutputStream fos = new FileOutputStream("temp/hello.dat");

        fos.write(65);
        fos.write(66);
        fos.write(67);
        // 파일 출력 스트림을 닫아 리소스를 해제한다.
        fos.close();

        // FileInputStream을 생성하여 "temp/hello.dat" 파일에서 바이트 데이터를 읽을 준비를 한다.
        FileInputStream fis = new FileInputStream("temp/hello.dat");

        System.out.println(fis.read());
        System.out.println(fis.read());
        System.out.println(fis.read());
        // 파일에서 더 이상 읽을 데이터가 없으므로 -1을 반환한다.
        System.out.println(fis.read()); // 출력: -1 (EOF, End Of File)
        // 파일 입력 스트림을 닫아 리소스를 해제한다.
        fis.close();

        // 두 번째 FileInputStream을 생성하여 같은 파일을 다시 읽는다.
        FileInputStream fis2 = new FileInputStream("temp/hello.dat");
        int data; // read() 메서드는 읽을 데이터가 없을 때 -1을 반환하므로 int 타입이 필요하다.
        while( (data = fis2.read()) != -1 ) { //  -1 (EOF, End Of File) 이 되기까지 루프
            System.out.println(data); // 읽은 바이트 값을 출력한다.
        }
        fis2.close();
    }
}
