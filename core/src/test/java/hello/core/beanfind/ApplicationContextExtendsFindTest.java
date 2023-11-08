package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac =
            new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입으로 조회, 자식이 둘 이상 있으면, 중복 오류가 발생한다.")
    void findBeanByParentTypeDuplicate(){
        //부모타입으로 조회해서 자식 인스턴스 두개가 조회된다 .
        //그래서 NoUniqueBeanDefinitionException 발생하면 테스트 성공
        assertThrows(NoUniqueBeanDefinitionException.class,
                ()-> ac.getBean(DiscountPolicy.class)
                );
    }

    //빈이름은 누가 지정하지? : 스프링 컨테이너에 빈등록할 때 그 이름으로 등록되는구나
    @Test
    @DisplayName("부모 타입으로 조회, 자식이 둘 이상 있으면, 빈이름을 지정하면된다.")
    void findBeanByParentTypeBeanName(){
        DiscountPolicy bean = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    //안 좋은 방법이다. 특정 하위 타입으로 조회. 하면 왜 안좋지?
    @Test
    @DisplayName("하위 타입으로 조회, 자식이 둘 이상 있으면, 빈이름을 지정하면된다.")
    void findBeanBySubType(){
        RateDiscountPolicy bean = ac.getBean( RateDiscountPolicy.class);
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }
    //부모타입으로 전부 다 조회
    @Test
    @DisplayName("부모타입으로 모두 조회하기")
    void findAllBeanByParent(){
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for (String key : beansOfType.keySet()) {
            System.out.println("key =" + key + " value =" + beansOfType.get(key));
            
        }

    }

    @Test
    @DisplayName("부모타입으로 모두 조회하기 -Object")
    void findAllBeanByObject(){
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key =" + key + " value =" + beansOfType.get(key));

        }

    }



    @Configuration
    static class TestConfig{
        @Bean
        public DiscountPolicy rateDiscountPolicy(){
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy(){
            return new FixDiscountPolicy();
        }

    }
}
