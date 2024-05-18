package com.example.ktrip.gijin.Service;

import com.example.ktrip.gijin.domain.Member;
import com.example.ktrip.gijin.dto.JoinDto;
import com.example.ktrip.gijin.service.JoinService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class JoinServiceTest {


    private final JoinService joinService;
    @Autowired
    public JoinServiceTest(JoinService joinService) {
        this.joinService = joinService;
    }

    @Test
    void joinProcess() {
        JoinDto joinDto =new JoinDto();
        joinDto.setId("12sd");
        joinDto.setPassword("a1243f");
        joinService.joinProcess(joinDto);





    }
}