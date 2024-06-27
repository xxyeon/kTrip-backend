package Iniro.kTrip.config;

import Iniro.kTrip.dto.CustomLogoutFilter;
import Iniro.kTrip.handler.OAuth2SuccessHandler;
import Iniro.kTrip.jwt.JWTFilter;
import Iniro.kTrip.jwt.JWTUtil;
import Iniro.kTrip.jwt.LoginFilter;
import Iniro.kTrip.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final DefaultOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/oauth2/**", "/signUp", "/signIn", "/trip", "/trip/*", "/trip/**").permitAll()
                        .requestMatchers("/reissue").permitAll()
                        .anyRequest().authenticated()
                )

//                .formLogin(form -> form
//                        .loginPage("/login/oauth2/code/naver")
//                        .defaultSuccessUrl("/", true)
//                        .permitAll()
//                )

                .logout(logout -> logout
                        .permitAll()
                )

                .oauth2Login(oauth2 -> oauth2
                        //.loginPage("/login-oauth2")
                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/login/oauth2/code/naver"))
                        .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // oauth2 설정
        //                .oauth2Login(oauth -> // OAuth2 로그인 기능에 대한 여러 설정의 진입점
        //                // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
        //                    oauth.userInfoEndpoint(c -> c.userService(oAuthService))
        //                        // 로그인 성공 시 핸들러
        //                        .successHandler(OAuthSuccessHandler)

        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, memberRepository, "/signIn"), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CustomLogoutFilter(jwtUtil, memberRepository), LogoutFilter.class);
        http.addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
