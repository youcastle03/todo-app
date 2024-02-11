package com.springboot.myfirstwebapp.login;



import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes("name")
public class WelcomeController {
    

    //GET요청이면 login으로 redirection
    @RequestMapping(value="/",method= RequestMethod.GET) //value를 login에서 /로 변경
    public String gotoWelcomePage(ModelMap model){
        model.put("name", getLoggedinUsername()); //하드코딩 대신 getLoggedinUsername()호출
        return "welcome";  //login return에서 welcome으로 변경
    }

    private String getLoggedinUsername(){
        Authentication authentication =    //인증 세부정보
        SecurityContextHolder.getContext().getAuthentication(); //실제로 우리에게 현재 인증된 주체를 제공한다,
        return authentication.getName(); //사용자이름 리턴
    }

}
/*
    @RequestMapping("login")
    public String gotoLoginPage(@RequestParam String name, ModelMap model){
        //RequestParam 을 통해 URL에서 값을 받아서 컨트롤러 코드에 전달 할 수 있다.
        //jsp에 전달은 어떻게 할까?-> Model!!
        model.put("name",name); //model에 이름이 name인 속성을 넣는다 .
        //name으로 해놓으면 무엇이 들어오든간 name변수에 들어오게 되고
        //이름을 name으로 해서 model에 넣게 된다.

        return "login";
    }
    */
/*  이로직은 이제 Spring security를 사용하면서 필요가 없어졌다.
    private AuthenticationService authenticationService;
    //생성자 주입
    public LoginController(AuthenticationService authenticationService){
        super();
        this.authenticationService=authenticationService;
    }
    //POST요청이면 welcome으로 redirection
    @RequestMapping(value="login",method= RequestMethod.POST)
    public String gotoWelcomePage(@RequestParam String name, @RequestParam String password, ModelMap model){
        if(authenticationService.authenticate(name,password)) {
            model.put("name", name);
            //패스워드는 출력하지 않을꺼니까 안넣음
            return "welcome";
        }
        //올바르지 않은 사용자일떄 에러메세지 출력하기 위해 model에 넣음
        model.put("errorMessage", "InValid Credectials! Please try again");
        return "login";
    }*/