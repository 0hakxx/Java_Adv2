package reflection;

import reflection.data.BasicData; // 'BasicData' 클래스를 사용할 거예요. 이 클래스의 정보를 알아볼 겁니다.
import java.lang.reflect.Modifier; // 클래스나 멤버의 접근 제어자(public, private 등) 정보를 분석하는 데 사용
import java.util.Arrays;

public class BasicV2 {

    public static void main(String[] args) {
        // 'BasicData' 클래스의 Class 객체를 얻는다. 모든 리플렉션 작업의 시작점
        Class<BasicData> basicData = BasicData.class;

        // 1. 클래스 이름 정보 조회
        // 풀 패키지 경로를 포함한 클래스 이름
        System.out.println("basicData.getName() = " + basicData.getName());
        // 패키지 경로를 제외한 순수한 클래스 이름
        System.out.println("basicData.getSimpleName() = " + basicData.getSimpleName());
        // 클래스가 속한 패키지 정보를 Package 객체로 반환
        System.out.println("basicData.getPackage() = " + basicData.getPackage());

        // 2. 상속 및 인터페이스 정보 조회
        // 클래스의 부모 클래스(super class) 정보를 Class 객체로 반환합니다.
        // 자바의 모든 클래스는 명시적으로 다른 클래스를 상속하지 않아도,
        // 기본적으로 'java.lang.Object' 클래스를 상속받습니다.
        // 따라서 BasicData가 다른 클래스를 'extends'하지 않았다면,
        // 이 메서드는 'class java.lang.Object'를 반환합니다.
        System.out.println("basicData.getSuperclass() = " + basicData.getSuperclass());
        // 클래스가 구현하고 있는 인터페이스들의 Class 객체 배열을 반환합니다.
        // 구현된 인터페이스가 없다면 빈 배열이 출력되겠죠.
        System.out.println("basicData.getInterfaces() = " + Arrays.toString(basicData.getInterfaces()));

        // 3. 클래스의 종류 확인
        // 해당 Class 객체가 인터페이스인지 확인합니다. (boolean 반환)
        System.out.println("basicData.isInterface() = " + basicData.isInterface());
        // 해당 Class 객체가 열거형(enum)인지 확인합니다. (boolean 반환)
        System.out.println("basicData.isEnum() = " + basicData.isEnum());
        // 해당 Class 객체가 어노테이션(Annotation)인지 확인합니다. (boolean 반환)
        System.out.println("basicData.isAnnotation() = " + basicData.isAnnotation());

        // 4. 접근 제어자 및 기타 속성 조회 (Modifiers)
        int modifiers = basicData.getModifiers();
        System.out.println("basicData.getModifiers() = " + modifiers);
        // Modifier 클래스의 static 메서드를 사용하여 정수형 마스크 값에서 'public' 속성이 있는지 확인합니다.
        System.out.println("isPublic = " + Modifier.isPublic(modifiers));
        // Modifier 클래스의 toString() 메서드를 사용하여 정수형 마스크 값을 사람이 읽기 쉬운 문자열로 변환합니다.
        // 예를 들어, 'public'이라면 "public"이라고 출력될 거예요.
        System.out.println("Modifier.toString() = " + Modifier.toString(modifiers));
    }
}