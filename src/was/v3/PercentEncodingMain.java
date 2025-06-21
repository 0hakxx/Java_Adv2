package was.v3;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.*;

public class PercentEncodingMain {
/*
💡 여전히 웹에서의 URL은 전 세계 모든 장비의 호환성을 위해 보수적인 ASCII를 사용
   - 이로 인해 '가', '나', '다'와 같은 한글 문자(비-ASCII 문자)나  URL에서 특별한 의미를 가지는 문자(예: 공백)는 ASCII 코드의 표현 한계가 존재
   - 이를 처리하기 위해 클라이언트(웹 브라우저)는:
     - ASCII 코드로 표현할 수 있는 문자는 ASCII를 그대로 사용
     - ASCII로 표현할 수 없는 기타 문자는 UTF-8 문자셋을 기준으로 '퍼센트 인코딩'
💡 가' 문자의 URL 인코딩 과정 상세
1.  가'를 UTF-8 바이트 시퀀스로 변환, 예: '가'  → 0xEA, 0xB0, 0x80
2.  각 UTF-8 바이트를 퍼센트 인코딩 : 이렇게 생성된 %EA%B0%80는 모두 ASCII에 포함되는 문자(%와 A~F, 0~9)들이기 때문에 URL에 안전하게 표현되어 전송이 가능.
    0xEA → %EA
    0xB0 → %B0
    0x80 → %80
3.  서버에서의 디코딩 과정:
    클라이언트로부터 %EA%B0%80와 같은 퍼센트 인코딩된 문자열을 수신합니다.
    서버는 각 `%` 기호를 제거하고, 뒤따르는 16진수 문자들(예: 'EA', 'B0', '80')을 원래의 16진수 바이트 값 (0xEA, 0xB0, 0x80)으로 다시 변환합니다.
    이렇게 얻은 3개의 바이트를 모아 UTF-8 디코딩을 수행하면, 최종적으로 "가"라는 원래의 한글 문자 확인
*/
    public static void main(String[] args) {
        String encode = URLEncoder.encode("가", UTF_8);
        System.out.println("encode = " + encode); // 결과 출력 예시: URL encode = %EA%B0%80
        String decode = URLDecoder.decode(encode, UTF_8);
        System.out.println("decode = " + decode); // 결과 출력 예시: URL decode = 가
    }
}