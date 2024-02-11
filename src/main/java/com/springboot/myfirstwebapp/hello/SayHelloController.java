package com.springboot.myfirstwebapp.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller //어노테이션을 추가함으로써 Bean임을 알려줘라
public class SayHelloController {

    //언제 리턴해야할까? URL을 호출했을때!!
    @RequestMapping("say-hello") //이 URL로 이동했을떄~
    @ResponseBody //이 메세지가 리턴한 것 그대로를 브라우저에 리턴
    public String sayHello(){
        //이 문자열을 반환한다.
        return "Hello! What are you learning today?";
        //이러면 오류가 나는데 Spring MVC는 문자열을 리턴할때  
        // 그 이름으로 된 뷰를 검색하기 때문이다.
        //아직 뷰가 없으므로 오류가 나는것.
        //그래서 ResponseBody가 필요
    }
    @RequestMapping("say-hello-html") //이 URL로 이동했을떄~
    @ResponseBody
    public String sayHelloHtml(){
        StringBuffer sb= new StringBuffer();
        sb.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Hard Cording HTML Page</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    Hard Cording HTML \n" +
                "</body>\n" +
                "</html>");
        return sb.toString();
    }
    @RequestMapping("say-hello-jsp") //이 URL로 이동했을떄~
     //뷰를 반환하므로 @Response Body가 필요하지 않다
    public String sayHelloJsp(){
        return "sayHello";

    }

}
