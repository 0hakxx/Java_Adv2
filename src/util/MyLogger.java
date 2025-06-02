package util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public abstract class MyLogger { // 'abstract' 키워드는 이 클래스가 직접 객체로 생성될 수 없음을 의미합니다.

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    /**
     `import static util.MyLogger.log;`와 같이 `static import` 구문을 사용하여
     클래스 이름(`MyLogger.`) 없이 `log()`로 바로 호출하기 위해서는,
     `log` 메서드가 반드시 `static` 멤버여야 합니다.
     클래스 자체에는 `static` 키워드를 붙일 수 없지만 (내부 클래스는 예외),
     클래스 내부의 멤버(변수, 메서드)에는 `static`을 붙여
     객체 생성 없이 클래스 이름을 통해 직접 접근할 수 있도록 합니다.
     */
    public static void log(Object obj) {
        String time = LocalTime.now().format(formatter);
        String threadName = Thread.currentThread().getName();
        System.out.printf("%s [%9s] %s\n", time, threadName, obj);
    }
}