package hello.core.beanfinder;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInforTest {

    AnnotationConfigApplicationContext ac
            = new AnnotationConfigApplicationContext(AppConfig.class);
    @Test
    @DisplayName("모든 빈출력하기")
    void findAllBean(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName); //모든 빈보니 모든 빈의 타입 몰라서 Object로 가져옴
            System.out.println("name = " + beanDefinitionName + "object"+ bean);
            
        }
    }

    @Test
    @DisplayName("애플리케이션  빈 출력하기")
    void findApplicationBean(){
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            //우리가 등록한 빈만 출력
            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                Object bean = ac.getBean(beanDefinitionName); //모든 빈보니 모든 빈의 타입 몰라서 Object로 가져옴
                System.out.println("name = " + beanDefinitionName + "object "+ bean);

            }


        }
    }
}
