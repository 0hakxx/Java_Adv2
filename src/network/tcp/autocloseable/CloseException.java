package network.tcp.autocloseable;

// Exception을 상속받아 체크 예외
public class CloseException extends Exception {
    public CloseException(String message) {
            super(message);
        }
}
