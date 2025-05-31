package io.file.copy;

import java.io.FileInputStream; // (V3에서는 직접 사용되지 않음. 이전 버전과의 비교를 위해 존재할 수 있음)
import java.io.FileOutputStream; // (V3에서는 직접 사용되지 않음. 이전 버전과의 비교를 위해 존재할 수 있음)
import java.io.IOException; // I/O 작업 중 발생할 수 있는 예외를 처리하기 위한 클래스 임포트
import java.nio.file.Files; // 파일 및 디렉토리 작업을 위한 NIO.2의 핵심 유틸리티 클래스 임포트
import java.nio.file.Path; // 파일 또는 디렉토리 경로를 나타내는 NIO.2 인터페이스 임포트
import java.nio.file.StandardCopyOption; // 파일 복사 옵션을 정의하는 Enum 임포트 (예: REPLACE_EXISTING)

public class FileCopyMainV3 {

    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();


        Path source = Path.of("temp/copy.dat"); // 원본 파일의 경로
        Path target = Path.of("temp/copy_new.dat"); // 복사될 새 파일의 경로

        // =====================================================================
        // V1 V2와 V3의 주요 차이점은 바로 이 부분입니다.
        // =====================================================================

        // V1,V2는 파일의 데이터를 자바 메모리에 불러온 다음 새로운 파일을 씀
        // Files.copy가 가 훨씬 빠른 이유는 자바에 파일 데이터를 불러오지 않고, 운영체제의 파일 복사 기능을 사용한다.
        // 즉, 운영체제에 명령을 내림

        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

        // 파일 복사 작업 종료 시간을 기록하고, 총 소요 시간을 계산하여 출력합니다.
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
    }
}