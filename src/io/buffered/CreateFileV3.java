package io.buffered;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static io.buffered.BufferedConst.*;

public class CreateFileV3 {

    public static void main(String[] args) throws IOException {
        // 파일에 바이트 단위로 데이터를 출력하기 위한 FileOutputStream 생성
        FileOutputStream fos = new FileOutputStream(FILE_NAME);

        // BufferedOutputStream을 사용하여 출력 스트림에 버퍼링 기능 추가
        // BUFFER_SIZE만큼 데이터를 모아서 한 번에 출력함으로써 디스크 I/O 횟수를 줄임
        BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER_SIZE);

        // 실행 시간 측정을 위한 시작 시각 기록
        long startTime = System.currentTimeMillis();

        // FILE_SIZE(예: 10MB)만큼 1바이트 값을 반복해서 출력
        // BufferedOutputStream 내부 버퍼에 먼저 저장되고, 버퍼가 가득 차면 한 번에 디스크로 기록됨
        for (int i = 0; i < FILE_SIZE; i++) {
            bos.write(1);
        }

        // 스트림 닫기 전에 내부 버퍼에 남아있는 데이터를 모두 디스크에 flush하고 자원 해제
        bos.close();

        // 종료 시각 기록
        long endTime = System.currentTimeMillis();

        // 결과 출력
        System.out.println("File created: " + FILE_NAME);
        System.out.println("File size: " + FILE_SIZE / 1024 / 1024 + "MB");
        System.out.println("Time taken: " + (endTime - startTime) + "ms");

        // [V2 대비 차이점]
        // V2는 개발자가 byte[] 배열과 인덱스를 직접 관리하여 버퍼링 로직을 수동으로 구현함
        // V3는 BufferedOutputStream을 사용하여 버퍼링을 자동으로 처리함 → 코드가 간결하고 유지보수 쉬움
        // 기능적으로는 유사하지만, V3는 자바의 표준 라이브러리에서 제공하는 고수준 스트림을 활용하여 가독성과 안정성을 높임
        // 또한 flush(), close() 처리 등도 BufferedOutputStream이 내부적으로 잘 처리해줌
    }
}
