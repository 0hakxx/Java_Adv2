package chat.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static util.MyLogger.log; // MyLogger 유틸리티에서 log 메서드를 정적 임포트

public class SessionManager {

    // 현재 활성화된 모든 세션을 저장하는 리스트
    private List<Session> sessions = new ArrayList<>();

    public synchronized void add(Session session) {
        sessions.add(session);
    }

    public synchronized void remove(Session session) {
        sessions.remove(session);
    }

    public synchronized void closeAll() {
        for (Session session : sessions) {
            session.close(); // 각 세션을 닫음
        }
        sessions.clear(); // 세션 목록 비우기
    }

    public synchronized void sendAll(String message) {
        for (Session session : sessions) {
            try {
                session.send(message); // 각 세션에 메시지 전송
            } catch (IOException e) {
                log(e.getMessage()); // 메시지 전송 중 오류 발생 시 로깅
            }
        }
    }

    public synchronized List<String> getAllUsername() {
        List<String> usernames = new ArrayList<>(); // 사용자 이름을 저장할 새 리스트
        for (Session session : sessions) {
            if (session.getUsername() != null) { // 사용자 이름이 null이 아닌 경우에만 추가
                usernames.add(session.getUsername());
            }
        }
        return usernames; // 사용자 이름 목록 반환
    }
}