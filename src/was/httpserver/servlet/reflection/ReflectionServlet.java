package was.httpserver.servlet.reflection;

import was.httpserver.HttpRequest;
import was.httpserver.HttpResponse;
import was.httpserver.HttpServlet;
import was.httpserver.PageNotFoundException;
import was.v6.SiteControllerV6;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectionServlet implements HttpServlet {

    private final List<Object> controllers;

    public ReflectionServlet(List<Object> controllers) {
        this.controllers = controllers;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        String path = request.getPath(); // HTTP 요청에서 경로(예: "/site1", "/search")를 추출

        //SiteControllerV6, SearchControllerV6
        for (Object controller : controllers) {
            // SiteControllerV6
            Class<?> aClass = controller.getClass();
            Method[] methods = aClass.getDeclaredMethods();
            // site1, site2
            for (Method method : methods) {
                String methodName = method.getName();
                // 예: 요청 경로가 "/search" 이고, 메서드 이름이 "search" 이면 매핑
                if (path.equals("/" + methodName)) {
                    // 일치하는 메서드를 찾았다면, 해당 메서드를 동적으로 호출
                    invoke(controller, method, request, response);
                    return;
                }
            }
        }
        // 모든 컨트롤러를 탐색했지만 요청 경로에 해당하는 메서드를 찾지 못한 경우
        throw new PageNotFoundException("request = " + path);
    }

    private static void invoke(Object controller, Method method, HttpRequest request, HttpResponse response) {
        try {
            // method.invoke(대상 객체, 인자1, 인자2, ...)
            // 찾은 메서드를 `controller` 객체에 대해 `request`와 `response`를 인자로 넘겨 호출
            method.invoke(controller, request, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
