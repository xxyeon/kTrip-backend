package Iniro.kTrip.controller;


import Iniro.kTrip.dto.JoinDto;
import Iniro.kTrip.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody//api서버를 만들거니깐
public class JoinController
{
    private final JoinService joinService;


    @Autowired
    public JoinController(JoinService joinService) {
        this.joinService = joinService;

    }

    @PostMapping("/join")
    public String joinProcess(JoinDto joinDto)
    {
        joinService.joinProcess(joinDto);
        return "join_form";
    }


}