package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {
    @Test
    void AutowriedOption(){
      ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);



    }
    //임의의 테스트 클래스 생성
    static class TestBean{

        //false되어있으면 에러 대신 메서드 자체가 호출안됨
        @Autowired(required = false)
        public void setNoBean1(Member noBean1){
            System.out.println("noBean1 = " + noBean1);

        }

        @Autowired
        public void setNoBean2(@Nullable Member noBean2){
            System.out.println("noBean1 = " + noBean2);

        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean3){
            System.out.println("noBean1 = " + noBean3);

        }

    }
}
