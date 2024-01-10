package hello.login.web.session;

import hello.login.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {
    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {

        //세션 생성
        //서버에서 쿠키 세션을 다 만들고 응답이 나갔다고 가정(쿠키 response에 담아놓음)
        MockHttpServletResponse response = new MockHttpServletResponse();//http서블릿 필요해서 구현체 가져옴
        Member member = new Member();
        sessionManager.createSession(member, response);

        //요청에 응답에서 가져온 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies()); //mySessionId =12134

        //세션 조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        //세션 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();

    }
}
