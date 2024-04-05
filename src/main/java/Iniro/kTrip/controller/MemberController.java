package Iniro.kTrip.controller;

import Iniro.kTrip.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import Iniro.kTrip.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @RequestMapping(value = {"/signUp"}, method = RequestMethod.POST)
    @PostMapping
    public String signUp(@RequestBody Member member){
        if (memberService.signUp(member) == true) return "/home";
        else return "/home/login";
    }

    @RequestMapping("/login")
    @GetMapping
    public String loginCheck(@RequestBody Member member){
        if (memberService.loginCheck(member) == true) return "/home";
        else return "/home/login";
    }
}
