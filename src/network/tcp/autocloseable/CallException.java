package network.tcp.autocloseable;
// Exception을 상속받아 체크 예외
public class CallException extends Exception {
    public CallException(String message) {
        super(message);
    }
}