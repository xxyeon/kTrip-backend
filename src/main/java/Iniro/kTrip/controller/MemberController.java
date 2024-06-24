package Iniro.kTrip.controller;

import Iniro.kTrip.domain.Member;
import Iniro.kTrip.domain.NaverMemberInfo;
import Iniro.kTrip.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final AuthService authService;

    @RequestMapping(value = {"/signUp"}, method = RequestMethod.POST)
    @PostMapping
    public String signUp(@RequestBody Member member){
        if (authService.signUp(member))
            //return ResponseEntity.status(HttpStatus.OK).body(member.toString());
            return "/home";
        else return "/home/login";
    }

    @RequestMapping("/login")
    @GetMapping
    public ResponseEntity<String> login(@RequestBody Member request){
        String token = this.authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

}
