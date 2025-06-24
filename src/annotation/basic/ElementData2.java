package annotation.basic;
// - tags = "t1": 배열이지만 단일 값일 경우 중괄호 생략 가능
// - count: 'count' 요소는 명시적으로 값을 할당하지 않았지만 AnnoElement 어노테이션 정의에 따라 'count'의 기본값인 '0'이 적용
@AnnoElement(value = "data", tags = "t1") // AnnoElement 어노테이션 적용
public class ElementData2 {
}