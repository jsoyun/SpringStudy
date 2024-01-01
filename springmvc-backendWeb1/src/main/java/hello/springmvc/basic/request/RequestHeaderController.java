package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {




    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletRequest response,
                          HttpMethod httpMethod ,
                          Locale locale,
                          @RequestHeader MultiValueMap<String, String> headerMap, //헤더 키 값 전부받기
                          @RequestHeader("hostkey") String hostvariable, //헤더값 중 특정값만 받아옴, 지금은 헤더중 host만 -> 헤더의 이름 매핑
                          @CookieValue(value = "cookieName", required = false) String cookie //쿠키받아옴 false면 없어도됨


                          ) {

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", hostvariable);
        log.info("myCookie={}", cookie);
        return "ok";

    }

}


