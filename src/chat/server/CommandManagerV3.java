package chat.server;

import chat.server.command.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandManagerV3 implements CommandManager {

    private static final String DELIMITER = "\\|";
    private final Map<String, Command> commands = new HashMap<>();

    public CommandManagerV3(SessionManager sessionManager) {
        commands.put("/join", new JoinCommand(sessionManager));
        commands.put("/message", new MessageCommand(sessionManager));
        commands.put("/change", new ChangeCommand(sessionManager));
        commands.put("/users", new UsersCommand(sessionManager));
        commands.put("/exit", new ExitCommand());
    }

    @Override
    public void execute(String totalMessage, Session session) throws IOException {
        String[] args = totalMessage.split(DELIMITER);

        // 명령어 키 추출 (예: "/join")
        String key = args[0];
        // (예. 키가 "/joing" 이면 JoinCommand 객체 반환)
        Command command = commands.get(key);

        //키를 찾을 수 없다면 처리할 수 없는 명령어
        if (command == null) {
            session.send("처리할 수 없는 명령어 입니다: " + totalMessage);
            return;
        }
        // (예."JoinCommand" 클래스의 execute() 실행)
        command.execute(args, session);
    }
}
