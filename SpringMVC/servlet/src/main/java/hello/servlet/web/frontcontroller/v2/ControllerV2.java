package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV2 {
    //기존과 거의 똑같은데
    //반환을 MyView를 반환한다
    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
