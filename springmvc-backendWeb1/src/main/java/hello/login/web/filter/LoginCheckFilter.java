package hello.login.web.filter;

import hello.login.web.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whitelist = {"/", "/members/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        log.info("requestURI {}", requestURI);
        HttpServletResponse httpResponse = (HttpServletResponse) response;


        try {
            log.info("인증 체크 필터 시작{}", requestURI);
            //로그인을 체크해야하는 경로인지 확인
            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행()", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청 {}", requestURI);
                    //로그인으로 redirect 보내버린다. (현재 페이지 정보를 함께 포함해서 넘겨줘야 이후에 로그인에 성공했을 때 다시 그 페이지를 찾으러 가지 않아도 됨)
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return; //여기가 중요! 만약 세션이 없으면 로그인화면으로 리다이렉트할거니까 더이상 컨트롤러 호출하지 말고 끝내기 (필터를 더는 진행하지 않는다 이후 서블릿과 컨트롤러도더는 호출되지 않는다.
                }

            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e; //예외를 로깅가능하지만 톰캣(was)까지 예외를 보내주어야한다.
        } finally {
            log.info("인증체크필터 종료");

        }
    }

    //화이티 리스트의 경우 인증 체크 안함
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

}
