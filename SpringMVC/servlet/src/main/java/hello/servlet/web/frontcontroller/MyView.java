package hello.servlet.web.frontcontroller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class MyView {
    private String viewPath;
    public MyView(String viewPath){
        this.viewPath = viewPath;
    }
    //실제 view가 보여지도록 동작 - 렌더링
    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        System.out.println("viewPath = " + viewPath);
        dispatcher.forward(request, response);

    }

//모델에 있는 데이터를 다 꺼내서
    public void render(Map<String, Object> model, HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException  {

        modelToRequestAttribute(model, req);
        //jsp포워드
        RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
        dispatcher.forward(req, resp);
    }
    //다시보기
//렌더가 오면 모델이 있는 값들을 다 꺼내서
    //httpservletrequest에다가 setAttribute로 다 넣는다
    private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest req) {
        model.forEach((key, value) -> req.setAttribute(key,value));
    }
}
