package com.springboot.myfirstwebapp.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.function.Function;

@Configuration //Bean들의 설정이 포함될 것이기에 추가
public class SpringSecurityConfiguration {
    //일단 인메모리사용
    //InMemoryUserDetailsManager

    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager() {


        UserDetails userDetails1 = getUserDetails("castle", "0000");
        UserDetails userDetails2 = getUserDetails("admin", "1234");

        return new InMemoryUserDetailsManager(userDetails1, userDetails2);
    }

    private UserDetails getUserDetails(String username, String password) {
        Function<String, String> passwordEncoder //String을 입력값으로 받고 String을 리턴
                = input -> passwordEncoder().encode(input);  //어떤 input이 와도
        //passWordEncoder()는 그걸 인코딩 한 다음 사용자 세부정보를 설정한다.

        //UserDetails는 인터페이스이다.
        UserDetails userDetails = User.builder()
                .passwordEncoder(passwordEncoder)
                .username(username)  //인증, 로그인하는데 사용하는 자격증명
                .password(password)   //인증
                .roles("USER", "ADMIN") //애플리케이션 안에있는 역할
                .build();
        return userDetails;
    }

    //커스텀 인코더 하용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    //SecurityFilterChain은 인터페이스로서
    // HttpServletRequest에 매칭될 수 있는 필터 체인을 정의한다.
    // 웹 요청이 들어오면 언제나 먼저 이 체인이 그것을 처리하게 된다.
    // 즉 모든 요청에 대해 일치하는 필터 체인을 정의한다.
    // 파라미터는 HttpSecurity라는 객체이다.
    //HttpSecurity는 웹 기반 보안을 설정하도록 해준다.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated());
        http.formLogin(withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);
        http.headers().frameOptions().disable();

        return http.build();
    }


}
//    @Bean
//    public InMemoryUserDetailsManager createUserDetailsManager(){
//        //UserDetails는 인터페이스이다.
//        //withDefaultPasswordEncoder()는 이제 잘사용되지 않지만 일단 이거도 해보자
//        UserDetails userDetails=User.withDefaultPasswordEncoder()
//                .username("admin")  //인증, 로그인하는데 사용하는 자격증명
//                .password("0000")   //인증
//                .roles("USER","ADMIN") //애플리케이션 안에있는 역할
//                .build();
//
//        return new InMemoryUserDetailsManager(userDetails);
//    }
//}
  //  Function<String, String> passwordEncoder //String을 입력값으로 받고 String을 리턴
//                = input -> passwordEncoder().encode(input);  //어떤 input이 와도
//        //passWordEncoder()는 그걸 인코딩 한 다음 사용자 세부정보를 설정한다.
//
//        //UserDetails는 인터페이스이다.
//        UserDetails userDetails=User.builder()
//                                .passwordEncoder(passwordEncoder)
//                                .username(username)  //인증, 로그인하는데 사용하는 자격증명
//                                .password(password)   //인증
//                                .roles("USER","ADMIN") //애플리케이션 안에있는 역할
//                                .build();
