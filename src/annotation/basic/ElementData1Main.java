package annotation.basic;

import java.util.Arrays; // 배열을 문자열로 편리하게 출력하기 위해 사용

public class ElementData1Main {

    public static void main(String[] args) {
        // 1. ElementData1 클래스의 Class 객체를 얻습니다.
        // 리플렉션으로 어노테이션을 읽기 위한 첫 단계입니다.
        Class<ElementData1> annoClass = ElementData1.class;

        // 2. Class 객체로부터 특정 어노테이션(AnnoElement)의 인스턴스를 가져옵니다.
        // getAnnotation() 메서드는 해당 Class에 특정 어노테이션이 적용되어 있다면,
        // 그 어노테이션의 인스턴스(값을 포함하는)를 반환합니다. 없다면 null을 반환합니다.
        AnnoElement annotation = annoClass.getAnnotation(AnnoElement.class);

        // 3. 가져온 어노테이션 인스턴스에서 각 요소의 값을 추출하여 출력합니다.
        // 어노테이션 요소는 메서드 형태로 정의되므로, 일반 메서드 호출처럼 값을 가져올 수 있습니다.
        // 'value' 요소의 값을 가져와 출력
        String value = annotation.value();
        System.out.println("value = " + value); // 출력: value = data
        // 'count' 요소의 값을 가져와 출력
        // ElementData1에서는 count=10으로 명시했으므로 10이 출력됩니다.
        int count = annotation.count();
        System.out.println("count = " + count); // 출력: count = 10
        // 'tags' 요소의 값을 가져와 배열 형태로 출력
        String[] tags = annotation.tags();
        System.out.println("tags = " + Arrays.toString(tags)); // 출력: tags = [t1, t2]
    }
}