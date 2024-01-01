package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={},age={}", username, age);

        response.getWriter().write("ok");

    }


    @ResponseBody //를 통해 ok문자가 뷰로 사용되지 않고 그대로 http응답메시지에 넣어서 반환된다
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
        @RequestParam("username") String memberName,
        @RequestParam("age") int memberAge
    ) {

        log.info("username={},age={}", memberName, memberAge);

        return "ok";

    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
        @RequestParam String memberName, //변수명 같으면 없앨 수 있다
        @RequestParam int memberAge
    ) {

        log.info("username={},age={}", memberName, memberAge);

        return "ok";

    }


    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(
        String username, //어노테이션 없애도 된다 대신 요청 파라미터와 이름이 맞아야한다
        int age
    ) {

        log.info("username={},age={}", username, age);

        return "ok";

    }


    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
        @RequestParam(required = true) String username,
        @RequestParam(required = false) Integer age //필수값이 아니라서 null이 오려면 int-> Integer로 java의 Integer는 객체 null거눙
    ) {

        log.info("username={},age={}", username, age);

        return "ok";

    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
        @RequestParam(defaultValue = "guest") String username,
        @RequestParam(defaultValue = "-1") Integer age
    ) {

        log.info("username={},age={}", username, age);

        return "ok";

    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(
        @RequestParam Map<String, Object> paramMap
    ) {

        log.info("username={},age={}", paramMap.get("username"), paramMap.get("age"));

        return "ok";

    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(
        @ModelAttribute HelloData helloData) {

        log.info("helloData={}", helloData);

        return "ok";

    }
}
