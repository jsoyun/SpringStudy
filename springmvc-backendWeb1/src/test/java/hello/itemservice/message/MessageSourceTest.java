package hello.itemservice.message;

//import org.assertj.core.api.Assertions;

import hello.springmvc.SpringmvcApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootConfiguration
@SpringBootTest(classes = SpringmvcApplication.class)
public class MessageSourceTest {
    @Autowired
    MessageSource messageSource;


    @Test
    void helloMessage() {
        ;
        System.out.println(Locale.getDefault());
        Locale.setDefault(Locale.KOREA);
        System.out.println(Locale.getDefault());
        String result = messageSource.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
    }

    @Test
    void notFoundMessageCode() {
        Assertions.assertThatThrownBy(() -> messageSource.getMessage("no_code", null, null))
            .isInstanceOf(NoSuchMethodException.class);
    }
}
