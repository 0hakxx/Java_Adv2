package util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 간단한 로깅(Logging) 기능을 제공하는 추상(abstract) 유틸리티 클래스.
 * 이 클래스는 인스턴스화될 필요가 없으며, 정적(static) 메서드를 통해 직접 사용됩니다.
 */
public abstract class MyLogger { // 'abstract' 키워드는 이 클래스가 직접 객체로 생성될 수 없음을 의미합니다.


    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");


    public static void log(Object obj) {
        // Step 1: 현재 시스템의 로컬 시간을 가져와 미리 정의된 포맷터로 문자열 변환합니다.
        // LocalTime.now(): 현재 시, 분, 초, 나노초를 포함하는 LocalTime 객체를 얻습니다.
        // .format(formatter): 이 LocalTime 객체를 위에서 정의한 "HH:mm:ss.SSS" 형식의 문자열로 포맷팅합니다.
        String time = LocalTime.now().format(formatter);

        // Step 2: 현재 로그를 기록하는 스레드의 이름을 가져옵니다.
        // Thread.currentThread(): 현재 실행 중인 스레드 객체를 반환합니다.
        // .getName(): 해당 스레드의 이름을 반환합니다.
        String threadName = Thread.currentThread().getName();

        // Step 3: 포맷된 시간, 스레드 이름, 그리고 전달받은 로그 메시지를 함께 표준 출력(콘솔)에 출력합니다.
        // System.out.printf(): 형식 문자열을 사용하여 포맷팅된 출력을 수행합니다.
        // %s: 문자열을 위한 형식 지정자입니다.
        // [%9s]: 문자열을 위해 9칸의 공간을 할당하고, 오른쪽 정렬합니다. (스레드 이름이 9칸보다 짧으면 공백으로 채워집니다.)
        // %s\n: 마지막으로 로그 메시지를 출력하고 줄바꿈합니다.
        System.out.printf("%s [%9s] %s\n", time, threadName, obj);
    }
}