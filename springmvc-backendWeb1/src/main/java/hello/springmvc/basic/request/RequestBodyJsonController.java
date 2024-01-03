package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {
    private ObjectMapper objecrMapper = new ObjectMapper();

    @PostMapping("request-body-json-v1")
    public void requestBodyJonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info(messageBody);
        HelloData helloData = objecrMapper.readValue(messageBody, HelloData.class);
        log.info(String.valueOf(helloData));

        response.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("request-body-json-v2")
    public String requestBodyJonV2(@RequestBody String messageBody) throws IOException {

        log.info(messageBody);
        HelloData helloData = objecrMapper.readValue(messageBody, HelloData.class);
        log.info(String.valueOf(helloData));

        return "ok";
    }

    @ResponseBody
    @PostMapping("request-body-json-v3")
    public String requestBodyJonV3(@RequestBody HelloData helloData) {

        log.info(String.valueOf(helloData));


        return "ok";
    }

    @ResponseBody
    @PostMapping("request-body-json-v4")
    public String requestBodyJonV3(HttpEntity<HelloData> httpEntity) {

        HelloData data = httpEntity.getBody();
        log.info(String.valueOf(data));


        return "ok";
    }

    @ResponseBody
    @PostMapping("request-body-json-v5")
    public HelloData requestBodyJonV4(HttpEntity<HelloData> httpEntity) {

        HelloData data = httpEntity.getBody();
        log.info(String.valueOf(data));


        return data;
    }


}
