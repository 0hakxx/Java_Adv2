package network.tcp.autocloseable;


//v1 의 문제점을 해결하기 위해 v2 코드를 작성하였으나 큰 문제가 발생함
public class ResourceCloseMainV2 {

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
        // V1과의 첫 번째 핵심 차이점: resource1과 resource2를 null로 초기화
        // 이는 try 블록 외부에서 선언하여 finally 블록에서도 접근할 수 있도록 하기 위함
        ResourceV1 resource1 = null;
        ResourceV1 resource2 = null;

        try {
            resource1 = new ResourceV1("resource1");
            resource2 = new ResourceV1("resource2");

            resource1.call();
            resource2.callEx(); // CallException을 발생시키는 메서드를 호출
        } catch (CallException e) {
            // V1은 이 예외를 logic 메서드 밖(main 메서드)으로 바로 던졌습니다.
            // V2는 여기서 CallException을 잡은 후, finally으로 처리
            System.out.println("ex: " + e);
            throw e;
        } finally {
            if (resource2 != null) {
                //위에서 CallException이 터져서, finally으로 자원을 닫으려 했으나 자원을 닫는 중 CloseException 발생
                resource2.closeEx(); // CloseException 발생
            }
            if (resource1 != null) {
                resource1.closeEx(); // resource2.closeEx()에서 CloseException이 발생하면 이 코드 호출 안됨!
            }
        }
    }
}