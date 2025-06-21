package reflection;

import reflection.data.BasicData;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodV2 {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 1. 정적(Static) 메서드 호출: 컴파일 시점에 결정되는 일반적인 호출 방식
        BasicData helloInstance = new BasicData();
        helloInstance.call(); // 코드를 변경하지 않는 이상 '정적' 호출

        // 2. 동적(Dynamic) 메서드 호출: 런타임에 메서드 이름 등을 유연하게 결정
        System.out.println("\n===== 동적(Dynamic) 메서드 호출 - 리플렉션 사용 =====");
        Class<? extends BasicData> helloClass = helloInstance.getClass();
        String methodName = "hello"; // 호출할 메서드 이름을 문자열 변수로 정의 (동적 변경 가능)
        // hello 메서드를 method 객체로 반환
        Method method1 = helloClass.getDeclaredMethod(methodName, String.class);
        // hello 메서드는 String 인자 값을 받으므로 hi 라는 인자를 넣어 실행
        Object returnValue = method1.invoke(helloInstance, "hi");
        System.out.println("returnValue = " + returnValue); // 메서드 반환 값 출력
    }
}