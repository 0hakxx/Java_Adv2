package io.buffered;

import java.io.FileInputStream;
import java.io.IOException;

import static io.buffered.BufferedConst.BUFFER_SIZE;
import static io.buffered.BufferedConst.FILE_NAME;

public class ReadFileV2 {

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(FILE_NAME);
        long startTime = System.currentTimeMillis();

        byte[] buffer = new byte[BUFFER_SIZE];
        int fileSize = 0;
        int size;

        // 읽은 바이트 수를 size에 저장하고, 파일 끝(EOF)이 아니면 반복 계속
        while ((size = fis.read(buffer,0,buffer.length)) != -1) {
            fileSize += size;
        }
        fis.close();

        long endTime = System.currentTimeMillis();
        System.out.println("File name: " + FILE_NAME);
        System.out.println("File size: " + fileSize / 1024 / 1024 + "MB");
        System.out.println("Time taken: " + (endTime - startTime) + "ms");

        // [V1 대비 차이점]
        // V1은 read()를 통해 1바이트씩 읽으며 반복 → 매 반복마다 디스크 I/O가 발생 → 속도가 느림
        // V2는 read(byte[])를 통해 BUFFER_SIZE만큼 한번에 읽음 → 디스크 접근 횟수가 줄어들어 성능이 개선됨
        // 즉, V2는 사용자 버퍼를 사용하여 읽기 성능을 최적화한 방식임
    }
}
