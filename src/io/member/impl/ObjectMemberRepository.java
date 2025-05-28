package io.member.impl;

import io.member.Member;           // Member 객체 정의를 임포트
import io.member.MemberRepository; // MemberRepository 인터페이스 정의를 임포트

import java.io.*;                 // 파일 입출력 관련 클래스들을 임포트 (ObjectOutputStream, ObjectInputStream, FileOutputStream, FileInputStream)
import java.util.ArrayList;       // List 인터페이스의 구현체인 ArrayList를 임포트
import java.util.List;            // List 인터페이스를 임포트

/**
 * 이 방식은 DataMemberRepository (원시 데이터 타입 저장) 방식과 비교했을 때 다음과 같은 장단점이 있습니다.
 *
 * 장점 (DataMemberRepository 대비):
 * 1.  객체 단위 저장: Member 객체의 각 필드를 개별적으로 읽고 쓸 필요 없이, Member 객체 자체(또는 Member 객체들의 리스트)를 통째로 저장하고 로드할 수 있습니다. 이로 인해 코드가 훨씬 간결해집니다.
 * 2.  데이터 구조 변경 용이성 (어느 정도): 객체 내부에 새로운 필드가 추가되더라도, 기본 직렬화 규칙을 따르면 기존 파일과의 호환성을 어느 정도 유지할 수 있습니다 (serialVersionUID 관리 필요).
 * 3.  복잡한 객체 저장 가능: Member 객체 내부에 또 다른 객체가 포함되어 있더라도, 그 객체들이 직렬화 가능하다면 전체 객체 그래프를 한 번에 저장할 수 있습니다.
 *
 * 단점 (DataMemberRepository 대비):
 * 1.  성능 오버헤드: 객체를 바이트 스트림으로 직렬화/역직렬화하는 과정 자체가 원시 데이터 타입을 직접 다루는 것보다 더 많은 CPU 시간과 메모리를 소모할 수 있습니다.
 * 2.  파일 전체 다시 쓰기: `add` 메소드에서 기존 파일을 '읽어와서' 리스트에 추가한 후, '전체 리스트를 다시 파일에 쓰는' 방식으로 동작합니다. 회원이 많아질수록 효율이 급격히 떨어집니다. (기존 파일에 append 할 수 없습니다.)
 * 3.  직렬화 가능 요구: Member 클래스가 반드시 `java.io.Serializable` 인터페이스를 구현해야 합니다. (이 인터페이스는 마커 인터페이스로, 객체가 직렬화 가능함을 JVM에 알리는 역할을 합니다.)
 * 4.  역직렬화 시 `ClassCastException` 위험: `readObject()`의 반환 타입이 `Object`이므로, 실제 객체 타입으로 캐스팅할 때 `ClassCastException`이 발생할 수 있습니다.
 * 5.  보안 취약점: 악의적인 직렬화된 객체를 역직렬화할 경우, 원치 않는 코드 실행으로 이어질 수 있는 보안 취약점이 존재합니다 (Java Serialization Gadgets 등).
 * 6.  버전 관리 어려움: 클래스 구조가 변경될 경우 `serialVersionUID`를 적절히 관리하지 않으면 `InvalidClassException`이 발생하여 기존 파일을 읽지 못할 수 있습니다.
 */
// =========> 이러한 이유로 실무에서는 큰 버그가 발생할 수 있는 경우가 많아 잘 사용하지 않는다. 따라서 개념정도만 알아두자 <==============

public class ObjectMemberRepository implements MemberRepository {

    // 회원 객체들이 직렬화되어 저장될 바이너리 파일의 경로를 상수로 정의
    private static final String FILE_PATH = "temp/members-obj.dat";

    @Override
    public void add(Member member) {
        // 1. 기존 파일의 모든 회원 정보를 먼저 읽어옵니다.
        //    (ObjectOutputStream은 append 모드를 직접 지원하지 않아, 기존 데이터를 덮어쓰기 때문)
        List<Member> members = findAll(); // findAll() 메소드를 호출하여 현재 저장된 모든 회원 리스트를 가져옴

        // 2. 새로 추가할 회원 객체를 리스트에 추가합니다.
        members.add(member);

        // 3. 수정된 전체 회원 리스트를 파일에 덮어씁니다.
        // try-with-resources 구문을 사용하여 ObjectOutputStream을 자동으로 닫도록 처리
        // FileOutputStream: 파일에 바이트 스트림을 쓰는 객체
        //   FILE_PATH: 데이터를 쓸 파일 경로
        //   (append 모드 'true'가 없으므로, 기본적으로 파일을 덮어쓰게 됩니다.)
        // ObjectOutputStream: FileOutputStream 위에 래핑하여 자바 객체를 바이트 스트림으로 직렬화하여 쓸 수 있게 합니다.
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            // Member 객체 리스트 전체를 파일에 직렬화하여 씁니다.
            // 이때 List<Member>와 Member 클래스는 반드시 Serializable 인터페이스를 구현해야 합니다.
            oos.writeObject(members);
        } catch (IOException e) {
            // 파일 쓰기 중 발생할 수 있는 IOException을 잡아서 런타임 예외로 다시 던집니다.
            throw new RuntimeException(e);
        }
    }

    /**
     * 파일에 저장된 모든 회원 정보를 직렬화된 객체 형태로 읽어와 Member 객체 리스트로 반환합니다.
     *
     * @return 파일에서 읽어온 Member 객체들의 리스트. 파일이 없으면 빈 리스트를 반환합니다.
     * @throws RuntimeException 파일 읽기 중 IOException이나 역직렬화 중 ClassNotFoundException이 발생하면 런타임 예외로 래핑하여 던집니다.
     */
    @Override
    public List<Member> findAll() {
        // try-with-resources 구문을 사용하여 ObjectInputStream을 자동으로 닫도록 처리
        // FileInputStream: 파일에서 바이트 스트림을 읽는 객체
        // ObjectInputStream: FileInputStream 위에 래핑하여 바이트 스트림을 자바 객체로 역직렬화하여 읽을 수 있게 합니다.
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            // 파일에서 객체를 읽어옵니다. 반환 타입은 Object이므로 적절한 타입으로 캐스팅이 필요합니다.
            Object findObject = ois.readObject();
            // 읽어온 Object를 List<Member> 타입으로 안전하게 캐스팅하여 반환합니다.
            return (List<Member>) findObject;
        } catch (FileNotFoundException e) {
            // 파일이 존재하지 않는 경우 (예: 처음 실행 시)
            // 비어있는 회원 리스트를 반환하여 프로그램이 정상적으로 계속 실행되도록 합니다.
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            // IOException: 파일 읽기 중 발생할 수 있는 예외
            // ClassNotFoundException: 역직렬화 시 해당 클래스 정의를 찾을 수 없을 때 발생
            // 이 두 가지 예외를 잡아서 런타임 예외로 다시 던집니다.
            throw new RuntimeException(e);
        }
    }
}