package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor //final 파라미터 가진 생성자 자동주입
public class LogDemoController {
    private final LogDemoService logDemoService;
//    private final ObjectProvider<MyLogger> myLoggerProvider; //얘의 생존범위는 고객들어와서(http요청 ) 나갈때까지
private final MyLogger myLogger; //얘의 생존범위는 고객들어와서(htt
    @RequestMapping("log-demo")
    @ResponseBody //문자를 그대로 응답
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        //고객이 어떤 url요청했는지 알 수 있다
        String requestURL = request.getRequestURL().toString();
        System.out.println("myLogger = " + myLogger.getClass());


//       333 MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestURL(requestURL);
        
        myLogger.log("controller test");
        Thread.sleep(1000);
        logDemoService.logic("testId");
        return "Ok";
    }



}
