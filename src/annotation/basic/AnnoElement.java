package annotation.basic;

import util.MyLogger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AnnoElement {
    String value();
    int count() default 0;
    String[] tags() default {};

    //MyLogger data(); // 다른 타입은 적용X
    // 클래스 타입만 메타데이터로 허용됨
    Class<? extends MyLogger> annoData() default MyLogger.class;
}
