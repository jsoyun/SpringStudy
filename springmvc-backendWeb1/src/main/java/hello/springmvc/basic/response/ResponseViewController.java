package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class ResponseViewController {


    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
            .addObject("data", "hello!");
        return mav;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV1(Model model) {
        model.addAttribute("data", "hello!!");
        return "response/hello"; //뷰로 반환된다 템플릿 엔진 response 폴더 안에 있는 hello
    }

    //명시적이지 않아서 권장하지 않는다
    @RequestMapping("/response/hello") //url이 뷰 폴더구조와 같으면 리턴없어도 뷰 반환됨
    public void responseViewV3(Model model) {

    }
}

