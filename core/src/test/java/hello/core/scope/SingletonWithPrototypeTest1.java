package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class SingletonWithPrototypeTest1 {
    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);//조회
        System.out.println("prototypeBean1 = " + prototypeBean1);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);//조회
        System.out.println("prototypeBean2 = " + prototypeBean2);
        prototypeBean2.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);


    }

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class,PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);
        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
        System.out.println("count1 = " + count1);
        System.out.println("count2 = " + count2);

    }
    @Scope("singleton")
    static class ClientBean{
        //의존관계
//        private final PrototypeBean prototypeBean;//생성 시점에 주입

        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider ;

//        @Autowired //생성자
//        n public ClientBean(PrototypeBean prototypeBean) {
//
//            this.prototypeBean = prototypeBean;
//        }

            public int logic(){
                PrototypeBean prototypeBean = prototypeBeanProvider.get();
                prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;

        }


    }

    @Scope("prototype")
    static class PrototypeBean{
        private int count = 0;
        public void addCount(){
            count++;
        }
        public int getCount(){
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init "+ this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destory호출안됨");
        }
    }
}
