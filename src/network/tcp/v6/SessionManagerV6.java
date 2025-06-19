package network.tcp.v6;

import java.util.ArrayList;
import java.util.List;

// 여러 클라이언트 세션 객체를 안전하게 관리하기 위한 클래스.
// 세션 추가/제거 및 모든 세션 종료 시 동시성 문제를 방지한다.
public class SessionManagerV6 {

    // 현재 활성화된 세션들을 저장하는 리스트
    private List<SessionV6> sessions = new ArrayList<>();

    // 새로운 세션을 리스트에 추가. 여러 스레드가 동시에 접근할 수 있으므로 'synchronized'로 동시성 문제 해결.
    public synchronized void add(SessionV6 session) {
        sessions.add(session);
    }

    // 세션을 리스트에서 제거. 역시 'synchronized'로 동시성 문제 해결.
    public synchronized void remove(SessionV6 session) {
        sessions.remove(session);
    }

    // 모든 활성 세션을 닫음. 역시 'synchronized'로, 모든 세션을 순차적으로 안전하게 닫도록 보장한다.
    public synchronized void closeAll() {
        // 루프를 돌면서 session.close()를 호출하여 각 세션의 자원을 해제한다.
        for (SessionV6 session : sessions) {
            session.close();
        }
        // 모든 세션이 닫힌 후 리스트를 비워준다.
        sessions.clear();
    }
}