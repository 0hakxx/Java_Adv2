package network.tcp.autocloseable;


public class ResourceCloseMainV1 {

    public static void main(String[] args) {
        try {
            logic(); // 자원을 사용하고 처리하는 로직 메서드 호출
        } catch (CallException e) {
            // 이 블록은 'CallException'을 catch한다,
            // 이 예외는 자원에서 'call' 작업을 수행하는 동안 문제가 발생했을 때 발생할 수 있는 사용자 정의 예외
            System.out.println("CallException 예외 처리"); // CallException 처리 메시지 출력
            throw new RuntimeException(e); // CallException을 RuntimeException으로 감싸서 다시 throw한 뒤, 프로그램 실행을 런타임 오류와 함께 중단
        } catch (CloseException e) {
            // 이 블록은 'CloseException'을 catch한다.
            // 이 예외는 자원에서 'close' 작업을 수행하는 동안 문제가 발생했을 때 발생할 수 있는 사용자 정의 예외
            System.out.println("CloseException 예외 처리"); // CloseException 처리 메시지 출력
            throw new RuntimeException(e); // CloseException을 RuntimeException으로 감싸서 다시 throw한 뒤, 프로그램 실행을 런타임 오류와 함께 중단
        }
    }


    // 이 메서드는 'CallException' 또는 'CloseException'을 던질 수 있다고 선언되어 있으므로,
    // 호출자(이 경우 main 메서드)가 이 예외들을 처리해야 한다.
    private static void logic() throws CallException, CloseException {
        ResourceV1 resource1 = new ResourceV1("resource1");
        ResourceV1 resource2 = new ResourceV1("resource2");

        resource1.call();
        resource2.callEx();   // resource2의 callEx 메서드를 호출, 이 메서드는 CallException을 발생시키도록 설계

        // 아래의 코드는 위의 resource2.callEx()에서 CallException이 발생하여 실행되지 않는다.
        System.out.println("자원 정리"); // 호출 안됨
        // 예외 발생으로 인해 아래의 자원 정리 코드 또한 실행되지 않는다.
        resource2.closeEx(); // 호출 안됨
        resource1.closeEx(); // 호출 안됨
    }
}