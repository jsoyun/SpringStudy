package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name= "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //[status line]
      response.setStatus(HttpServletResponse.SC_OK); //기본 성공

        //[response-headers]
//        response.setHeader("Content-Type","text/plain;charset=utf-8");
        //캐시 무효화
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        //내가 원하는 임의의 헤더
        response.setHeader("my-header", "hello");

//        content(response);
//        cookie(response); 쿠키 만들기
        redirect(response); //리다이렉트


        PrintWriter writer = response.getWriter();
        writer.println("ok");

    }

    private void redirect(HttpServletResponse response) throws IOException {
        //status code 302
        //Location: /basic/hello-form.html

//        response.setStatus(HttpServletResponse.SC_FOUND); //302
//        response.setHeader("Location", "/basic/hello-form.html");
        //위 두 줄이 아래와 같다.
        response.sendRedirect("/basic/hello-form.html");


    }

    private void cookie(HttpServletResponse response) {
        //아래와 같다 Set-Cookie: myCookie = good; Max-Age= 600;
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }

    private void content(HttpServletResponse response) {
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
    }


}
