package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV4 controller = (ControllerV4) handler;
        //특정 구현체로 하면 안되는 이유중 하나가
        //이 상위 컨트롤러로 해야 form에만 쓰는게 아니라 list, save로도 쓸 수 있어서 그렇구나
//        MemberFormControllerV4 controller = (MemberFormControllerV4) handler;
        System.out.println("controlleV4핸들러어뎁터의 handle메소드 안 = " + controller);
        //paramMap
        Map<String, String> paramMap = new HashMap<>();
        createParamMap(request, paramMap);

        //  반 model 객체
        Map<String,Object> model = new HashMap<>();
        //모델뷰 설정을 이곳 어댑터에서 해준다.
        System.out.println("앞model = " + model);
        String viewName = controller.process(paramMap, model);
        System.out.println("뒤model = " + model);
        System.out.println("viewName = " + viewName);

        ModelView mv = new ModelView(viewName);
        mv.setModel(model);
        System.out.println(" set하고 직후!!!= " + mv.getModel());
        return mv;
    }


    private static void createParamMap(HttpServletRequest request, Map<String, String> paramMap) {
        request.getParameterNames().asIterator().forEachRemaining(name ->

                paramMap.put(name, request.getParameter(name))
        );
    }
}
