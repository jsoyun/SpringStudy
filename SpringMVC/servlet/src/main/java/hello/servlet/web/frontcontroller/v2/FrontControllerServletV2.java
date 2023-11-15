package hello.servlet.web.frontcontroller.v2;


import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name ="frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
//url 에 v2 하위에 어떤 경로가 들어와도 이 서블릿이 호출된다
public class FrontControllerServletV2 extends HttpServlet {
    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    //해당 프론트 서블릿이 생성될 때 매핑 정보를 미리 담아둘 것이다
    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());



    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("-------FrontControllerServletV2.service---------------");
       ///front-controller/v2/members 이 url 가져옴
        String requestURI = req.getRequestURI();//요청한 url 받을 수 있다.
        System.out.println("requestURI  = " + requestURI);
        //다형성, 부모는 자식을 담을 수 있다. 부모타입으로 참조변수 호출한다
        ControllerV2 controller = controllerMap.get(requestURI);
        if (controller == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND );
            return;
        }
        //있으면 프로세스 호출
        MyView view = controller.process(req, resp);
        view.render(req,resp);
    }
}
