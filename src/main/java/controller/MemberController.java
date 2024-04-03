package controller;

import domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.MemberService;

@RestController
public class MemberController {

    MemberService memberService;

//    @RequestMapping(value = {"/signUp"}, method = RequestMethod.POST)
//    //@PostMapping
//    public String signUp(@RequestBody Member member){
//        if (memberService.signUp(member) == true) return "/home";
//        else return "/home/login";
//    }

    @RequestMapping("/login")
    @GetMapping
    public String loginCheck(@RequestBody Member member){
        if (memberService.loginCheck(member) == true) return "/home";
        else return "/home/login";
    }
}
