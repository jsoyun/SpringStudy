package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {


    @Test
    void statefulServiceSingleTon(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulServiceBean1 = ac.getBean(StatefulService.class);
        StatefulService statefulServiceBean2 = ac.getBean(StatefulService.class);
         //ThreadA: A 사용자가 10000원을 주문
        int userAPrice = statefulServiceBean1.order("userA", 10000);
        //ThreadB: B 사용자가 20000원을 주문
        int userBPrice = statefulServiceBean2.order("userB", 20000);
//        int price1 = statefulServiceBean1.getPrice();
//         nint price2 = statefulServiceBean2.getPrice();
        //같은 이유는? 위에 보면 stateful1, stateful2라고 했지만 어쨌는 참조하고 있는 객체는 같아서
        // 사
        System.out.println("price1 = " + userAPrice);
        System.out.println("price2 = " + userBPrice);
        Assertions.assertThat(userBPrice).isEqualTo(20000);

    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }

    }
}