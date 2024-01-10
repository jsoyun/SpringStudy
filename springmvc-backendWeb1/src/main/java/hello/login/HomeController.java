package hello.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.SessionConst;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    public String home() {
        return "basic02/home";
    }

    //    @GetMapping("/") //로그인안한 사용자도 들어와야해서 required false 지정
//    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
//        if (memberId == null) {
//            return "basic02/home";
//        }
//        //로그인
//        Member loginMember = memberRepository.findById(memberId);
//        if (loginMember == null) {
//            return "basic02/home";
//        }
//
//        model.addAttribute("member", loginMember);
//        return "basic02/loginHome";
//
//    }

//    @GetMapping("/") //로그인안한 사용자도 들어와야해서 required false 지정
//    public String homeLoginV2(HttpServletRequest request, Model model) {
//        //세션 관리자에 저장된 회원 정보 조회
//        Member member = (Member) sessionManager.getSession(request);
//
//        if (member == null) {
//            return "basic02/home";
//        }
//
//        model.addAttribute("member", member);
//        return "basic02/loginHome";
//
//    }


//    @GetMapping("/") //로그인안한 사용자도 들어와야해서 required false 지정
//    public String homeLoginV3(HttpServletRequest request, Model model) {
//
//        HttpSession session = request.getSession(false); //세션 생성될 수 있으므로 false
//        if (session == null) {
//            return "basic02/home";
//        }
//        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
//
//        //세션에 호ㅣ원데이터 없으면
//        if (loginMember == null) {
//            return "basic02/home";
//        }
//
//        model.addAttribute("member", loginMember);
//        return "basic02/loginHome";
//
//    }

//    @GetMapping("/") //로그인안한 사용자도 들어와야해서 required false 지정
//    public String homeLoginV3(HttpServletRequest request, Model model) {
//
//        HttpSession session = request.getSession(false); //세션 생성될 수 있으므로 false
//        if (session == null) {
//            return "basic02/home";
//        }
//        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
//
//        //세션에 호ㅣ원데이터 없으면
//        if (loginMember == null) {
//            return "basic02/home";
//        }
//
//        model.addAttribute("member", loginMember);
//        return "basic02/loginHome";
//
//    }

    //    @GetMapping("/") //로그인안한 사용자도 들어와야해서 required false 지정
    public String homeLoginV3Spring(
        //name에 대한 값이 있으면 꺼내준다.
        @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        //세션에 호ㅣ원데이터 없으면
        if (loginMember == null) {
            return "basic02/home";
        }

        model.addAttribute("member", loginMember);
        return "basic02/loginHome";

    }

    @GetMapping("/")
    public String homeLoginV3ArgumentResolver(@Login Member loginMember, Model model) {

        //세션에 호ㅣ원데이터 없으면
        if (loginMember == null) {
            return "basic02/home";
        }

        model.addAttribute("member", loginMember);
        return "basic02/loginHome";

    }


}
