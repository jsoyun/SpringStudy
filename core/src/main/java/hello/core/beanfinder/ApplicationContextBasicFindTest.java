package hello.core.beanfinder;

import hello.core.AppConfig;
import hello.core.member.MemberService;

import static org.assertj.core.api.Assertions.*;


import hello.core.member.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class ApplicationContextBasicFindTest {


    AnnotationConfigApplicationContext ac
            = new AnnotationConfigApplicationContext(AppConfig.class);
    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByBean(){
        MemberService memberService =
                ac.getBean("memberService", MemberService.class);
        System.out.println("memberService = " + memberService);
        System.out.println("memberService.getClass = " + memberService.getClass());

        //검증
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

    }

    @Test
    @DisplayName("이름없이 타입으로만 조회")
    void findBeanByType(){
        MemberService memberService =
                ac.getBean( MemberService.class); //인터페이스를 조회해서
        //검증
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

    }
    //지금까지 인터페이스를 조회해서 impl로 인터페이스의 구현체가 대상이 되었다.

    @Test
    @DisplayName("구현객체 타입으로 조회")
    void findBeanByName2(){
        MemberServiceImpl memberService =
                ac.getBean("memberService", MemberServiceImpl.class); //이코드는 구현에 집중한거라서 좋은 코드는 안됨
        //검증
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

    }

    //테스트는 실패 테스트를 만들어야한다.
    @Test
    @DisplayName("빈 이름으로 조회X")
    void findBeanByNameX(){
        MemberService xxxx = ac.getBean("xxx", MemberService.class);
//        assertThat(memberService).isInstanceOf(memberService.getClass());
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, ()->{
                    ac.getBean("xxx", MemberService.class);
        }
        );

    }

}
