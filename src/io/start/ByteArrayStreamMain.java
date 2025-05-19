package io.start;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ByteArrayStreamMain {

    public static void main(String[] args) throws IOException {
        // 입력으로 사용할 바이트 배열 만들기 (값: 1, 2, 3)
        byte[] input = {1, 2, 3};

        // 메모리에 데이터를 저장할 수 있는 출력 스트림 만들기
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // input 배열의 데이터를 출력 스트림에 쓰기
        baos.write(input);  // 내부적으로 메모리에 저장됨

        // 출력된 데이터를 다시 읽을 수 있도록 입력 스트림 만들기
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        // 입력 스트림에서 모든 데이터를 읽어서 배열에 저장
        byte[] bytes = bais.readAllBytes();

        // 읽은 바이트 배열을 출력 (결과: [1, 2, 3])
        System.out.println(Arrays.toString(bytes));
    }
}
