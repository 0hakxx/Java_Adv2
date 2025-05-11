package charset;

import java.nio.charset.Charset;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.*;

public class EncodingMain2 {

    // EUC-KR과 MS949는 자바의 표준 문자셋 상수(StandardCharsets)에 포함되어 있지 않다.
    // (StandardCharsets에는 UTF-8, UTF-16, ISO_8859_1, US_ASCII 등만 있음)
    // 그래서 Charset.forName("EUC-KR"), Charset.forName("MS949")로 직접 생성해서 상수로 선언함.
    private static final Charset EUC_KR = Charset.forName("EUC-KR");
    private static final Charset MS_949 = Charset.forName("MS949");

    public static void main(String[] args) {
        System.out.println("== 영문 ASCII 인코딩 ==");
        test("A", US_ASCII, US_ASCII);         // 정상: ASCII로 인코딩/디코딩
        test("A", US_ASCII, ISO_8859_1);       // 정상: ASCII로 인코딩된 바이트는 ISO_8859_1로 디코딩해도 동일 문자로 복원됨 (두 문자셋의 0~127 영역이 완전히 호환됨) ASCII 확장(LATIN-1)
        test("A", US_ASCII, EUC_KR);           // 정상: ASCII로 인코딩된 바이트는 EUC-KR로 디코딩해도 동일 문자로 복원됨 (EUC-KR도 0~127은 ASCII와 동일)
        test("A", US_ASCII, MS_949);           // 정상: ASCII로 인코딩된 바이트는 MS949로 디코딩해도 동일 문자로 복원됨 (MS949도 0~127은 ASCII와 동일)
        test("A", US_ASCII, UTF_8);            // 정상: ASCII로 인코딩된 바이트는 UTF-8로 디코딩해도 동일 문자로 복원됨 (UTF-8도 0~127은 ASCII와 동일)
        test("A", US_ASCII, UTF_16BE);         // 오류/깨짐: ASCII 1바이트를 UTF-16BE(2바이트 단위)로 디코딩하면 올바른 문자로 복원되지 않음

        System.out.println("== 한글 인코딩 - 기본 ==");
        test("가", US_ASCII, US_ASCII);         // 오류/깨짐: ASCII는 한글을 인코딩할 수 없으므로 '?' 등으로 대체됨
        test("가", ISO_8859_1, ISO_8859_1);     // 오류/깨짐: ISO_8859_1도 한글 인코딩 불가, '?' 등으로 대체됨
        test("가", EUC_KR, EUC_KR);             // 정상: EUC-KR로 인코딩/디코딩, 한글 복원 가능
        test("가", MS_949, MS_949);             // 정상: MS949로 인코딩/디코딩, 한글 복원 가능
        test("가", UTF_8, UTF_8);               // 정상: UTF-8로 인코딩/디코딩, 한글 복원 가능
        test("가", UTF_16, UTF_16);             // 정상: UTF-16으로 인코딩/디코딩, 한글 복원 가능

        System.out.println("== 한글 인코딩 - 복잡한 문자 ==");
        test("뷁", EUC_KR, EUC_KR);             // 오류/깨짐: EUC-KR은 '뷁' 같은 확장 한글을 지원하지 않으므로 '?' 등으로 대체됨
        test("뷁", MS_949, MS_949);             // 정상: MS949는 확장 한글(예: '뷁') 지원, 복원 가능
        test("뷁", UTF_8, UTF_8);               // 정상: UTF-8은 모든 한글 지원, 복원 가능
        test("뷁", UTF_16BE, UTF_16BE);         // 정상: UTF-16BE도 모든 유니코드 문자 지원, 복원 가능

        System.out.println("== 한글 인코딩 - 디코딩이 다른 경우 ==");
        test("가", EUC_KR, MS_949);             // 정상: MS949는 EUC-KR과 호환, '가'는 복원 가능
        test("뷁", MS_949, EUC_KR);             // 오류/깨짐: MS949로 인코딩된 확장 한글을 EUC-KR로 디코딩하면 복원 불가(깨짐), 인코딩 가능, 디코딩 X
        test("가", EUC_KR, UTF_8);              // 오류/깨짐: EUC-KR로 인코딩된 바이트를 UTF-8로 디코딩하면 복원 불가(깨짐)
        test("가", MS_949, UTF_8);              // 오류/깨짐: MS949로 인코딩된 바이트를 UTF-8로 디코딩하면 복원 불가(깨짐)
        test("가", UTF_8, MS_949);              // 오류/깨짐: UTF-8로 인코딩된 바이트를 MS949로 디코딩하면 복원 불가(깨짐)

        System.out.println("== 영문 인코딩 - 디코딩이 다른 경우");
        test("A", EUC_KR, UTF_8);
        test("A", MS_949, UTF_8);
        test("A", UTF_8, MS_949);
        test("A", UTF_8, UTF_16BE);             // X
    }


    private static void test(String text, Charset encodingCharset, Charset decodingCharset) {
        // 1. 인코딩: 문자열을 encodingCharset로 바이트 배열로 변환
        byte[] encoded = text.getBytes(encodingCharset);

        // 2. 디코딩: 위에서 얻은 바이트 배열을 decodingCharset로 다시 문자열로 변환
        String decoded = new String(encoded, decodingCharset);

        System.out.printf("%s -> [%s] 인코딩 -> %s %sbyte -> [%s] 디코딩 -> %s\n",
                text, encodingCharset, Arrays.toString(encoded), encoded.length,
                decodingCharset, decoded);
    }
}
