package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping(value = {"/hello-basic","/hello-basic2"} )
    public String helloBasic(){
        log.info("helloBasic");
        return "ok";

    }

    //요즘 많이 쓰는 경로변수 - 최근 http api는 다음과같이 리소스 경로에 식별자 넣는 스타일 선호한다

//경로변수명과 파라미터 이름 같이면 생략가능
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data){
        log.info("mappingPath userId={}", data);
        return "ok";
    }


    //다중 매핑
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingLongPath(@PathVariable String userId, @PathVariable Long orderId){
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    //특정 파라미터 조건 - 잘 사용하진 않음
    //특정 파라미터 정보가 있어야 호출된다.
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam(){
        log.info("mappingParam");
        return "ok";
    }

    //특정헤더값이 있어야 실행된다
    //파라미터 매핑과 비슷하지만 http헤더를 사용한다
    @GetMapping(value="/mapping-header", headers = "mode=debug")
    public String mappingHeader(){
        log.info("mappingheader");
        return "ok";
    }

    //미디어타입 조건 매핑 - http요청 content-Type,consume
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes(){
        log.info("mappingConsume");
        return "ok";
    }

    //accept 헤더 기반 미디어타입
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces(){
        log.info("mappingProduces");
        return "ok";
    }




}
