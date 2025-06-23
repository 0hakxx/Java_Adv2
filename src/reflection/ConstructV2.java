package reflection;

import java.lang.reflect.Constructor; // 생성자 정보를 나타내는 'Constructor' 클래스를 사용합니다.
import java.lang.reflect.InvocationTargetException; // 리플렉션으로 메서드/생성자 호출 중 예외 발생 시 처리
import java.lang.reflect.Method;     // 메서드 정보를 나타내는 'Method' 클래스를 사용합니다.

public class ConstructV2 {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<?> aClass = Class.forName("reflection.data.BasicData");

        Constructor<?> constructor = aClass.getDeclaredConstructor(String.class);

        //'true'로 설정하면 해당 생성자의 접근 제한을 무시하고 호출할 수 있도록 허용
        constructor.setAccessible(true); // (주의: 보안상 문제가 될 수 있으므로 꼭 필요한 경우에만 사용)

        Object instance = constructor.newInstance("hello"); // 객체 생성
        System.out.println("instance = " + instance); // 생성된 인스턴스 출력 (toString() 메서드가 있다면 그 결과가 나옴)

        Method method1 = aClass.getDeclaredMethod("call");

        method1.invoke(instance); // 'instance' 객체의 'call' 메서드 실행
    }
}