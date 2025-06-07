package network.tcp.autocloseable; 
public class CallException extends Exception { // Exception을 상속받으므로, 이 예외는 "checked exception"이 됨
    // checked exception은 컴파일 시점에 예외 처리를 강제함
    // 즉, 이 예외를 발생시키는 메서드는 throws 선언을 하거나, 호출하는 쪽에서 try-catch로 처리해야 한다.
    public CallException(String message) { // CallException의 생성자
        super(message);
        // super(message)는 부모 클래스인 Exception의 생성자를 호출합
        // 즉, 이 예외가 발생했을 때 예외 메시지를 함께 전달할 수 있게 됨.
    }
}