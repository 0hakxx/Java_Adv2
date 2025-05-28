package io.member.impl;

import io.member.Member;           // Member 객체 정의를 임포트
import io.member.MemberRepository; // MemberRepository 인터페이스 정의를 임포트

import java.io.*;                 // 파일 입출력 관련 클래스들을 임포트
import java.util.ArrayList;       // List 인터페이스의 구현체인 ArrayList를 임포트
import java.util.List;            // List 인터페이스를 임포트

import static java.nio.charset.StandardCharsets.*; // UTF-8 문자열 인코딩을 static으로 임포트하여 간편하게 사용


public class FileMemberRepository implements MemberRepository {

    // 회원 데이터가 저장될 파일의 경로를 상수로 정의
    private static final String FILE_PATH = "temp/members-txt.dat";
    // 회원 정보 각 필드를 구분할 구분자(Delimiter)를 상수로 정의
    private static final String DELIMITER = ",";

    @Override
    public void add(Member member) {
        // try-with-resources 구문을 사용하여 BufferedWriter를 자동으로 닫도록 처리
        // FileWriter: 파일에 문자를 쓰는 객체
        //   FILE_PATH: 데이터를 쓸 파일 경로
        //   UTF_8: 파일 인코딩을 UTF-8로 지정 (한글 깨짐 방지)
        //   true: append 모드 (파일이 이미 존재하면 기존 내용 뒤에 추가, 없으면 새로 생성)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, UTF_8, true))) {
            // Member 객체의 정보를 구분자(DELIMITER)로 연결하여 한 줄로 만듭니다.
            bw.write(member.getId() + DELIMITER + member.getName() + DELIMITER + member.getAge());
            // 파일에 새로운 줄을 추가합니다. (각 회원은 한 줄에 저장)
            bw.newLine();
        } catch (IOException e) {
            // 파일 쓰기 중 발생할 수 있는 IOException을 잡아서 런타임 예외로 다시 던집니다.
            // 이렇게 하면 호출하는 쪽에서 반드시 IOException을 처리하지 않아도 됩니다.
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Member> findAll() {
        List<Member> members = new ArrayList<>(); // 회원 객체들을 저장할 리스트 초기화


        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH, UTF_8))) {
            String line; // 파일에서 한 줄씩 읽어올 문자열 변수
            // 파일에서 한 줄씩 읽어오면서 내용이 있는 동안 반복
            while ((line = br.readLine()) != null) {
                // 읽어온 한     줄(line)을 구분자(DELIMITER)를 기준으로 분리합니다.
                // 예: "id001,홍길동,30" -> ["id001", "홍길동", "30"]
                String[] memberData = line.split(DELIMITER);
                // 분리된 문자열 배열을 사용하여 새로운 Member 객체를 생성하고 리스트에 추가합니다.
                // memberData[0]은 ID, memberData[1]은 이름, memberData[2]는 나이(문자열이므로 Integer.valueOf로 변환)
                members.add(new Member(memberData[0], memberData[1], Integer.valueOf(memberData[2])));
            }
            return members; 
        } catch (FileNotFoundException e) {
            // 파일이 존재하지 않는 경우 (예: 회원이 없는 상태에서, 회원 목록 조회 실행 시)
            // 비어있는 회원 리스트를 반환하여 프로그램이 정상적으로 계속 실행되도록 합니다.
            return new ArrayList<>();
        } catch (IOException e) {
            // 파일 읽기 중 발생할 수 있는 다른 IOException을 잡아서 런타임 예외로 다시 던집니다.
            throw new RuntimeException(e);
        }
    }
}