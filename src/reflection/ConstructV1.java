package reflection;

import java.lang.reflect.Constructor; // 리플렉션에서 생성자 정보를 나타내는 'Constructor' 클래스를 임포트합니다.

public class ConstructV1 {

    public static void main(String[] args) throws ClassNotFoundException {
        // 런타임에 클래스 이름을 문자열로 받아 동적으로 로드
        Class<?> aClass = Class.forName("reflection.data.BasicData");

        System.out.println("===== getConstructors() =====");
        // 현재 클래스에 선언된 public 생성자만을 반환
        Constructor<?>[] constructors = aClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor);
        }

        System.out.println("===== getDeclaredConstructors() =====");
        // '직접 선언된' 모든 생성자들을 반환
        Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
        for (Constructor<?> constructor : declaredConstructors) {
            System.out.println(constructor);
        }
    }
}