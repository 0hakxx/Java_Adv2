package charset;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.SortedMap;

public class AvailableCharsetsMain {

    public static void main(String[] args) {
        // 1. 시스템에서 사용 가능한 모든 Charset(문자 인코딩) 목록을 조회
        //    - 이용 가능한 자바 표준 + 운영체제(OS)에서 지원하는 문자셋까지 모두 포함
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        for (String charsetName : charsets.keySet()) {
            System.out.println("charsetName = " + charsetName); // 지원되는 문자셋 이름 출력
        }

        System.out.println("=====");

        // 2. 문자셋 이름(대소문자 구분 없음)으로 Charset 객체를 조회
        //    - 예시: "MS949", "ms949", "x-windows-949" 등 다양한 이름 사용 가능
        Charset charset1 = Charset.forName("MS949");
        System.out.println("charset1 = " + charset1); // java.nio.charset.Charset[MS949] 형태로 출력

        // 3. 해당 Charset의 별칭(aliases) 목록 조회
        //    - 같은 문자셋을 여러 이름으로 부를 수 있음
        Set<String> aliases = charset1.aliases();
        for (String alias : aliases) {
            System.out.println("alias = " + alias); // 예: x-windows-949, windows-949 등
        }

        // 4. "UTF-8" 문자로 Charset 객체 조회 (대소문자 구분 없음)
        Charset charset2 = Charset.forName("UTF-8");
        System.out.println("charset2 = " + charset2); // java.nio.charset.Charset[UTF-8]

        // 5. StandardCharsets 상수로 UTF-8 Charset 객체 조회 (추천 방식, 컴파일 타임 상수)
        Charset charset3 = StandardCharsets.UTF_8;
        System.out.println("charset3 = " + charset3); // java.nio.charset.Charset[UTF-8]

        // 6. 시스템의 기본 Charset(운영체제/자바 실행 환경의 기본 문자셋) 조회
        Charset defaultCharset = Charset.defaultCharset();
        System.out.println("defaultCharset = " + defaultCharset); // 예: UTF-8, MS949 등 환경에 따라 다름
    }
}
