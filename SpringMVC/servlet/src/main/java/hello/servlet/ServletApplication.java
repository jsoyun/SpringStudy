package hello.servlet;

import hello.servlet.web.springmvc.v1.SpringMemberFormControllerV1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ServletComponentScan //서블릿 자동등록
@SpringBootApplication
public class ServletApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletApplication.class, args);
	}
	//스프링 부트가 설정정보 가져와서 아래 코드를 자동으로 해준다_직접 안해도 됨
//	@Bean
//	InternalResourceViewResolver internalResourceViewResolver(){
//		return new InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
//	}

	//컴포넌트 스캔을 할 수도 있고 빈으로 직접 등록할 수도 있다(똑같음)
//	@Bean
//	SpringMemberFormControllerV1 springMemberFormControllerV1(){
//		return new SpringMemberFormControllerV1();
//	}


}
