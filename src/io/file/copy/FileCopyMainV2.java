package io.file.copy;

import java.io.FileInputStream; // 파일을 바이트 스트림으로 읽기 위한 클래스 임포트
import java.io.FileOutputStream; // 파일을 바이트 스트림으로 쓰기 위한 클래스 임포트
import java.io.IOException; // I/O 작업 중 발생할 수 있는 예외를 처리하기 위한 클래스 임포트

public class FileCopyMainV2 {

    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();


        FileInputStream fis = new FileInputStream("temp/copy.dat");
        FileOutputStream fos = new FileOutputStream("temp/copy_new.dat");

        // =====================================================================
        // V1과 V2의 주요 차이점은 바로 이 부분입니다.
        // =====================================================================

        // V1 방식:
        // byte[] bytes = fis.readAllBytes(); // 1. 원본 파일의 모든 내용을 바이트 배열로 메모리에 한 번에 읽어옵니다.
        // fos.write(bytes); // 2. 읽어온 바이트 배열 전체를 새 파일에 한 번에 씁니다.
        // - 장점: 코드가 매우 간결합니다.
        // - 단점: 파일 크기가 매우 클 경우, 모든 내용을 메모리에 올리기 때문에 OutOfMemoryError가 발생할 수 있습니다.
        //        대용량 파일 복사에는 적합하지 않습니다.

        // V2 방식:
        // fis.transferTo(fos); // FileInputStream의 내용을 FileOutputStream으로 직접 전송합니다.
        // - 장점:
        //   1. 중간 버퍼를 자동으로 사용하며 효율적으로 데이터를 복사합니다.
        //      개발자가 명시적으로 버퍼를 생성하고 관리할 필요가 없습니다.
        //   2. 파일을 메모리에 통째로 로드하지 않기 때문에, 대용량 파일 복사에 매우 적합합니다.
        //      (JVM 내부적으로 최적화된 스트림 처리나, 심지어 운영체제의 최적화된 파일 전송 기능을 활용할 수도 있습니다.)
        //   3. 코드가 V1과 마찬가지로 매우 간결하며, 가독성이 좋습니다.
        // - 단점: Java 9 이상에서만 사용 가능합니다. (V1은 그 이전 버전에서도 사용 가능)

        fis.transferTo(fos); // Java 9부터 도입된 효율적인 파일 스트림 전송 메서드입니다.

        fis.close();
        fos.close();

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
    }
}