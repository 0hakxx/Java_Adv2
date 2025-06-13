package network.tcp.autocloseable;

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
        ResourceV1 resource1 = null;
        ResourceV1 resource2 = null;

        try {
            resource1 = new ResourceV1("resource1");
            resource2 = new ResourceV1("resource2");

            resource1.call();
            resource2.callEx(); // CallException을 발생시키는 메서드를 호출
        } catch (CallException e) {
            // V2는 여기서 CallException을 잡은 후, finally으로 처리
            System.out.println("ex: " + e);
            throw e;
        } finally {
            if (resource2 != null) {
                resource2.closeEx(); // CloseException 발생
            }
            if (resource1 != null) {
                // resource2.closeEx()에서 CloseException이 발생되어 이 코드 호출 안됨!
                // 즉 resouce2 자원 정리 도중 에러가 발생하여 해당 자원(resource1)이 자원 정리 실패됨
                resource1.closeEx();
            }
        }
    }
}