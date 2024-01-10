package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    //TODO:CHECK final 붙이는 이유가 생성했을 때만 값을 최종으로 매기고 변경하지 않겠다는 의미
    //컨트롤러 객체에서 loginService하나가 생성되어서 쓰이는건데 해당 객체가 종료되면 같이 종료되고
    //final을 쓰지 않았을 때 차이는?????
    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        log.info("[GetMapping/login]");
        return "/basic02/login/loginForm";  //로그인폼 보여줌
    }
//
//    @PostMapping("/login")
//    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
//        if (bindingResult.hasErrors()) {
//            return "/basic02/login/loginForm";
//        }
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//        if (loginMember == null) {
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다."); //reject하면 글로벌 오류이다.
//            return "/basic02/login/loginForm";
//        }
//        //로그인 성공 처리 TODO
//
//
//        //쿠키에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 모두 종료)
//        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
//        response.addCookie(idCookie);
//
//        return "redirect:/";
//
//    }


//    @PostMapping("/login")
//    public String loginV2(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
//        if (bindingResult.hasErrors()) {
//            return "/basic02/login/loginForm";
//        }
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//        if (loginMember == null) {
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다."); //reject하면 글로벌 오류이다.
//            return "/basic02/login/loginForm";
//        }
//        //로그인 성공 처리 TODO
//
//
//        //세션 관리자를 통해 세션을 생성하고, 회원 데이터 보관
//        sessionManager.createSession(loginMember, response);
//        return "redirect:/";
//
//    }

    //    @PostMapping("/login")
//    public String loginV3(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
//        if (bindingResult.hasErrors()) {
//            return "/basic02/login/loginForm";
//        }
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//        log.info("login? {}", loginMember);
//        if (loginMember == null) {
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다."); //reject하면 글로벌 오류이다.
//            return "/basic02/login/loginForm";
//        }
//        //로그인 성공 처리 TODO
//        //세션이 있으면 있는 세션 반환, 없으면 신규세션을 생성
//        HttpSession session = request.getSession();
//        //세션에 로그인 회원정보 보관
//        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
//
//
//        //세션 관리자를 통해 세션을 생성하고, 회원 데이터 보관
//
//        return "redirect:/";
//
//    }
    @PostMapping("/login")
    public String loginV4(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
                          @RequestParam(defaultValue = "/") String redirectURL,
                          HttpServletRequest request) {
        log.info("[PostMapping/login]--redirectURL----={}", redirectURL);
        if (bindingResult.hasErrors()) {
            log.info("[PostMapping/login]--bindingResult.hasErrors----");
            return "/basic02/login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("[PostMapping/login] login하고 Member객체={}", loginMember);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다."); //reject하면 글로벌 오류이다.
            return "/basic02/login/loginForm";
        }

        //로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);


        //세션 관리자를 통해 세션을 생성하고, 회원 데이터 보관

        return "redirect:" + redirectURL;

    }

//    @PostMapping("/logout")
//    public String logout(HttpServletResponse response) {
//        expireCookie(response, "memberId");
//        return "redirect:/";
//    }

//    @PostMapping("/logout")
//    public String logoutV2(HttpServletRequest request) {
//        sessionManager.expire(request);
//        return "redirect:/";
//    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            //세션과 그 안에 데이터 날라간다.
            session.invalidate();
        }
        return "redirect:/";
    }

    private static void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
