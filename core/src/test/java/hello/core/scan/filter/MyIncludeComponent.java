package hello.core.scan.filter;

import java.lang.annotation.*;

//어노테이션 공부해야한다
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {


}
