package was.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.*;
import static util.MyLogger.log;

public class HttpRequest {

    private String method; // HTTP 메서드 (GET, POST 등)
    private String path;   // 요청 경로 (예: /index.html, /search)
    private final Map<String, String> queryParameters = new HashMap<>(); // URL 쿼리 파라미터 (예: ?key=value) 저장
    private final Map<String, String> headers = new HashMap<>();         // HTTP 헤더 (예: Content-Type, Content-Length) 저장

    public HttpRequest(BufferedReader reader) throws IOException {
        parseRequestLine(reader); // HTTP 요청의 첫 줄(요청 라인)을 파싱
        parseHeaders(reader);     // HTTP 헤더들을 파싱
        parseBody(reader);        // HTTP 요청 바디를 파싱 (POST 요청 등에 해당)
    }

    private void parseRequestLine(BufferedReader reader) throws IOException {
        String requestLine = reader.readLine(); // "GET /path?query HTTP/1.1" 형태의 요청 라인 읽기
        if (requestLine == null) {
            throw new IOException("EOF: No request line received");
        }
        String[] parts = requestLine.split(" "); // 공백을 기준으로 메서드, 경로+쿼리, HTTP 버전으로 분리
        if (parts.length != 3) { // 메서드, 경로+쿼리, HTTP 버전 3개가 아닐 경우 예외
            throw new IOException("Invalid request line: " + requestLine);
        }
        method = parts[0]; // 첫 번째 부분은 HTTP 메서드 (예: "GET", "POST")
        // 경로 부분(예: "/path?name=value")을 '?' 기준으로 분리
        String[] pathParts = parts[1].split("\\?"); // "\\?"는 정규식에서 '?'를 리터럴 문자 그대로 해석하도록 함
        path = pathParts[0]; // 첫 번째 부분은 순수 경로 (예: "/path")
        // 쿼리스트링이 존재할 경우 (pathParts의 길이가 1보다 크면)
        if (pathParts.length > 1) {
            parseQueryParameters(pathParts[1]); // 두 번째 부분(쿼리스트링)을 파싱
        }
    }

    private void parseQueryParameters(String queryString) {
        for (String param : queryString.split("&")) { // 각 파라미터 쌍을 '&' 기준으로 분리
            String[] keyValue = param.split("="); // 각 파라미터 쌍을 '=' 기준으로 키와 값으로 분리
            String key = URLDecoder.decode(keyValue[0], UTF_8); // 키를 URL 디코딩
            // 값이 없는 경우(예: "param=")를 처리하기 위해 keyValue.length > 1 확인
            String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], UTF_8) : ""; // 값을 URL 디코딩, 없으면 빈 문자열
            queryParameters.put(key, value); // 파싱된 키-값을 맵에 저장
        }
    }

    private void parseHeaders(BufferedReader reader) throws IOException {
        String line;
        while (!(line = reader.readLine()).isEmpty()) {
            String[] headerParts = line.split(":", 2); // 첫 번째 ':'만 기준으로 분리 (Value에 ':'가 포함될 수 있으므로)
            if (headerParts.length == 2) { // 유효한 Key:Value 형식인 경우에만 처리
                // 앞 뒤 공백 제거 (trim())하여 깔끔하게 맵에 저장
                headers.put(headerParts[0].trim(), headerParts[1].trim());
            } else {
                // 유효하지 않은 헤더 형식 처리 (예: ':'이 없거나 너무 많은 경우)
                log("Invalid header format: " + line);
            }
        }
    }

    private void parseBody(BufferedReader reader) throws IOException {
        // "Content-Length" 헤더가 없으면 바디가 없는 것으로 간주하고 즉시 반환
        if (!headers.containsKey("Content-Length")) {
            return;
        }

        // "Content-Length" 헤더의 값을 정수로 파싱하여 바디의 길이를 얻습니다.
        int contentLength = Integer.parseInt(headers.get("Content-Length"));
        char[] bodyChars = new char[contentLength]; // 바디 내용을 저장할 char 배열 생성

        // Content-Length만큼 바디를 읽어들입니다.
        // reader.read(char[])는 읽은 문자 수를 반환합니다.
        int read = reader.read(bodyChars, 0, contentLength);
        //int read = reader.read(bodyChars); // 기존 코드 (이 경우 한 번에 다 못 읽을 수도 있고 offset, length 명시가 더 안전)

        // 실제로 읽은 바이트 수가 Content-Length와 다르면 오류로 간주
        if (read != contentLength) {
            throw new IOException("Fail to read entire body. Expected " + contentLength + " bytes, but read " + read);
        }
        String body = new String(bodyChars); // 읽어들인 char 배열을 문자열로 변환
        log("HTTP Message Body: " + body); // 파싱된 바디 내용 로그 출력

        // "Content-Type" 헤더를 확인하여 바디의 MIME 타입을 파악합니다.
        String contentType = headers.get("Content-Type");
        // 만약 Content-Type이 "application/x-www-form-urlencoded" 이면
        // 바디 내용이 URL 쿼리 파라미터와 동일한 형식으로 인코딩된 것이므로,
        // 기존의 parseQueryParameters 메서드를 재활용하여 바디의 데이터를 쿼리 파라미터 맵에 추가합니다.
        if ("application/x-www-form-urlencoded".equals(contentType)) {
            parseQueryParameters(body);
        }
        // 다른 Content-Type (예: application/json)에 대한 처리는 여기에 추가할 수 있습니다.
    }

    public String getMethod() {
        return method;
    }
    public String getPath() {
        return path;
    }
    public String getParameter(String name) {
        return queryParameters.get(name);
    }
    public String getHeader(String name) {
        return headers.get(name);
    }
    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", queryParameters=" + queryParameters +
                ", headers=" + headers +
                '}';
    }
}