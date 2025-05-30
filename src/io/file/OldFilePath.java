package io.file;

import java.io.File;
import java.io.IOException;

public class OldFilePath {

    public static void main(String[] args) throws IOException {
        // "temp/.." 경로를 사용하여 File 객체를 생성합니다.
        // 이는 상대 경로이며, 현재 작업 디렉토리를 기준으로 합니다.
        File file = new File("temp/..");

        // getPath()는 File 객체가 생성될 때 사용된 경로 문자열을 그대로 반환합니다.
        // 따라서 콘솔에는 "temp\.."가 출력됩니다.
        System.out.println("path = " + file.getPath());


        // 절대 경로는  ".."와 같은 상대 경로 요소는 해석하지 않고 단순히 현재 디렉토리 뒤에 붙여서 경로를 만듭니다.
        // 따라서 "C:\Users\yeong\IdeaProjects\JavaAdv2\temp\.."가 출력됩니다.
        System.out.println("Absolute path = " + file.getAbsolutePath());


        // 정규 경로는 모든 상대 경로 요소(., ..)와 심볼릭 링크를 해석하여 실제 파일 시스템상의 최종 경로를 알려줍니다.
        // 따라서 "C:\Users\yeong\IdeaProjects\JavaAdv2\temp\.."는
        // "C:\Users\yeong\IdeaProjects\JavaAdv2"로 해석되어 출력됩니다.
        System.out.println("Canonical path = " + file.getCanonicalPath());

        // getCanonicalPath()로 얻은 경로(즉, C:\Users\yeong\IdeaProjects\JavaAdv2)에 있는
        // 파일과 디렉토리 목록을 가져옵니다.
        // 출력 결과를 보면 .git, .gitignore, .idea, JavaAdv2.iml, out, src, temp 등이 있습니다.
        File[] files = file.listFiles();
        if (files != null) { // 파일 목록이 null이 아닌지 확인하여 NullPointerException을 방지합니다.
            for (File f : files) {
                // 각 항목이 파일(F)인지 디렉토리(D)인지 표시하고 그 이름을 출력합니다.
                System.out.println( (f.isFile() ? "F" : "D")  + " | " + f.getName());
            }
        } else {
            // 파일을 찾을 수 없거나, 디렉토리가 존재하지 않거나, 접근 권한이 없는 경우에 출력됩니다.
            System.out.println("No files found or directory does not exist/is not accessible.");
        }
    }
}