package io.start;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class StreamStartMain3 {

    public static void main(String[] args) throws IOException {

        FileOutputStream fos = new FileOutputStream("temp/hello.dat");


        byte[] input = {65, 66, 67};


        fos.write(input);
        fos.close();

        FileInputStream fis = new FileInputStream("temp/hello.dat");

        // 읽은 데이터를 저장할 버퍼(배열) 생성 (크기 10)
        byte[] buffer = new byte[10];
        // buffer 배열의 인덱스 1부터 최대 9바이트를 읽어 저장
        // 즉, buffer[1] ~ buffer[9]에 데이터가 저장됨
        // 실제로 읽은 바이트 수를 반환 (여기선 3바이트)
        int readCount = fis.read(buffer, 1, 9);

        System.out.println("readCount = " + readCount);

        // buffer[1]~buffer[3]에 65,66,67('A','B','C')가 들어가고
        // 나머지는 0 (초기값)
        // 예시 출력: [0, 65, 66, 67, 0, 0, 0, 0, 0, 0]
        System.out.println(Arrays.toString(buffer));



        fis.close();
    }
}

