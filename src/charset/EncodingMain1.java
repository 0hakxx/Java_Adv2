package charset;

import java.nio.charset.Charset;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.*;

public class EncodingMain1 {

    // EUC-KR과 MS949는 자바의 표준 문자셋 상수(public final class StandardCharsets)에 포함되어 있지 않다.
    // (UTF-8, UTF-16, ISO_8859_1, US_ASCII 등만 있음)
    // 그래서 Charset.forName("EUC-KR"), Charset.forName("MS949")로 직접 생성해서 상수로 선언함.
    private static final Charset EUC_KR = Charset.forName("EUC-KR");
    private static final Charset MS_949 = Charset.forName("MS949");

    public static void main(String[] args) {
        // == ASCII 영문 처리 ==
        System.out.println("== ASCII 영문 처리 ==");
        encoding("A", US_ASCII);      // 미국 표준 ASCII (1바이트)
        encoding("A", ISO_8859_1);    // ISO-8859-1 (1바이트, 유럽계 단일 바이트 문자셋)
        encoding("A", EUC_KR);        // 한글용 EUC-KR (영문은 1바이트로 인코딩)
        encoding("A", UTF_8);         // UTF-8 (영문은 1바이트)
        encoding("A", UTF_16BE);      // UTF-16BE (영문도 2바이트로 인코딩)

        // == 한글 지원 ==
        System.out.println("== 한글 지원 ==");
        encoding("가", EUC_KR);       // EUC-KR (한글 2바이트)
        encoding("가", MS_949);       // MS949 (한글 2바이트, EUC-KR과 호환)
        encoding("가", UTF_8);        // UTF-8 (한글 3바이트)
        encoding("가", UTF_16BE);     // UTF-16BE (한글 2바이트)

    }
    private static void encoding(String text, Charset charset) {
        // 문자를 컴퓨터가 이해할 수 있는 숫자(Byte)로 변경하는 것을 인코딩이라 한다.
        // 주어진 문자열(text)을 지정한 문자셋(charset)으로 인코딩 한다.
        byte[] bytes = text.getBytes(charset);

        System.out.printf("%s -> [%s] 인코딩 -> %s %sbyte\n",
                text, charset, Arrays.toString(bytes), bytes.length);
    }



}
