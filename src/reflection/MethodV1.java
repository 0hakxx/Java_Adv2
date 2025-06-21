package reflection;

import reflection.data.BasicData; // 'BasicData' 클래스를 사용할 거예요. 이 클래스의 메서드 정보를 알아볼 겁니다.
import java.lang.reflect.Method; // 리플렉션에서 메서드 정보를 나타내는 'Method' 클래스를 임포트합니다.

public class MethodV1 {

    public static void main(String[] args) {
        // 'BasicData' 클래스의 Class 객체를 얻습니다. 모든 리플렉션 작업의 시작점이죠.
        Class<BasicData> helloClass = BasicData.class;

        System.out.println("===== getMethods() =====");
        // getMethods() 메서드는 현재 클래스와 그 모든 부모 클래스(상속 계층)에서
        // 'public' 접근 제어자를 가진 모든 메서드들을 반환합니다.
        Method[] methods = helloClass.getMethods();
        for (Method method : methods) {
            System.out.println("method = " + method);
        }

        System.out.println("===== getDeclaredMethods() =====");
        // getDeclaredMethods() 메서드는 접근 제어자 관계 없이 현재 클래스에 '직접 선언된' 모든 메서드들을 반환합니다.
        Method[] declaredMethods = helloClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            System.out.println("declaredMethod = " + method);
        }
    }
}