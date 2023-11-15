package hello.servlet.web.frontcontroller.v4;


import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet(name ="frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {
    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    //해당 프론트 서블릿이 생성될 때 매핑 정보를 미리 담아둘 것이다
    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("-------FrontControllerServletV2.service---------------");
       ///front-controller/v2/members 이 url 가져옴
        String requestURI = req.getRequestURI();//요청한 url 받을 수 있다.
        System.out.println("requestURI  = " + requestURI);
        //다형성, 부모는 자식을 담을 수 있다. 부모타입으로 참조변수 호출한다
        ControllerV4 controller = controllerMap.get(requestURI);
        if (controller == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND );
            return;
        }

        //이런 디테일한 로직은 메서드를 따로 빼는 것이 좋다 
        //paramMap을 넘겨줘야한다.
        Map<String, String> paramMap = createParamMap(req);
        Map<String, Object> model = new HashMap<>(); //추가
        String viewName = controller.process(paramMap, model);

        MyView view = viewResolver(viewName);
        view.render(model,req,resp);
    }

    private  MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private  Map<String, String> createParamMap(HttpServletRequest req) {
        Map<String, String> paramMap = new HashMap<>();

        req.getParameterNames().asIterator()
                .forEachRemaining(paramName -> {


                    paramMap.put(paramName, req.getParameter(paramName));
                    System.out.println("paramName  = " + paramName);
                    System.out.println("req.getParameter(paramName) = " + req.getParameter(paramName));
                    
                            
                            
                });
        return paramMap;
    }
}
