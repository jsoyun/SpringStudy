package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextBasicFindTest {
    AnnotationConfigApplicationContext ac =
            new AnnotationConfigApplicationContext(AppConfig.class);
    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName(){
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        System.out.println("memberService = " + memberService);
        System.out.println("MemberService.getClass()"+memberService.getClass());
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }


    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType(){
        MemberService memberService = ac.getBean(MemberService.class); //인터페이스로
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class); //구현객체
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2(){
        //구체 타입으로 조회하면 유연성이 떨어진다.
        MemberServiceImpl memberService = ac.getBean("memberService",MemberServiceImpl.class); //추상(인터페이스)아닌 구체 주현객체로 조회하는게 좋지는 않지만
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class); //구현객체
    }

    //항상 실패 태스트 만들어줘야함
    @Test
    @DisplayName("빈이름으로 조회x")
    void findBeanByNameX(){
//        MemberService xxx = ac.getBean("xxxx", MemberService.class);
        assertThrows(NoSuchBeanDefinitionException.class,
                ()-> ac.getBean("xxxx",MemberService.class));
    }

}
