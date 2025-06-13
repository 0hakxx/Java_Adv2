package network.tcp.autocloseable;


public class ResourceCloseMainV1 {

    public static void main(String[] args) {
        try {
            logic();
        } catch (CallException e) {
            System.out.println("CallException 예외 처리");
            throw new RuntimeException(e);
        } catch (CloseException e) {
            System.out.println("CloseException 예외 처리");
            throw new RuntimeException(e);
        }
    }

    private static void logic() throws CallException, CloseException {
        ResourceV1 resource1 = new ResourceV1("resource1");
        ResourceV1 resource2 = new ResourceV1("resource2");

        resource1.call();
        resource2.callEx();   // resource2의 callEx 메서드를 호출, 이 메서드는 CallException을 발생시키도록 설계

        // 아래의 코드는 위의 resource2.callEx()에서 CallException 체크 예외가 이 발생하여 실행되지 않는다.
        System.out.println("자원 정리"); // 호출 안됨
        // 예외 발생으로 인해 아래의 자원 정리 코드 또한 실행되지 않는다.
        resource2.closeEx(); // 호출 안됨
        resource1.closeEx(); // 호출 안됨
    }
}