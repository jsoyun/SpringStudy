package hello.core;

import hello.core.member.Member;
import hello.core.member.Grade;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        //스프링은 어떻게 생성을 하냐면
        //스프링은 모든게 ApplicationContext에서 시작을 한다
        //AppConfig.class 에 설정된 메서드로 객체 생성해서 스프링이 관리해준다.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        //기본적으로 메서드 이름이 등록되기 때문에 그걸로 가져와야한다. 이름, 타입
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);


        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member"+ member.getName());
        System.out.println("find Member"+ findMember.getName());


    }
}
