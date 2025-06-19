package network.tcp.autocloseable;

// AutoCloseable 인터페이스를 구현하여 try-with-resources 문에서 자동으로 닫힐 수 있게 만듦.
// close() 메서드를 오버라이딩 해야한다.
public class ResourceV2 implements AutoCloseable {

    private String name;

    public ResourceV2(String name) {
        this.name = name;
    }

    public void call() {
        System.out.println(name + " call");
    }

    public void callEx() throws CallException {
        System.out.println(name + " callEx");
        throw new CallException(name + " ex");
    }

    // AutoCloseable 인터페이스의 추상 메서드를 구현
    // 이 메서드는 try-with-resources 문이 끝날 때 자동으로 호출되어 리소스를 해제
    @Override
    public void close() throws CloseException {
        System.out.println(name + " close");
        throw new CloseException(name + " ex");
    }

}