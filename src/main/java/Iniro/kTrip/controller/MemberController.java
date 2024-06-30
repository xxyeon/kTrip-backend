package Iniro.kTrip.controller;

import Iniro.kTrip.domain.Member;
import Iniro.kTrip.domain.NaverMemberInfo;
import Iniro.kTrip.repository.MemberRepository;
import Iniro.kTrip.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/signUp")
public class MemberController {

    @Autowired
    private final MemberRepository memberRepository;
    private final AuthService authService;
    @PostMapping("/request")
    public ResponseEntity<String> signUp(@RequestBody Member member) {
        System.out.println(member);
        boolean signUpResult = authService.signUp(member);

        if (signUpResult) {
            return ResponseEntity.status(HttpStatus.OK).body("회원 가입 성공");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("회원 가입 실패");
        }
    }
    @GetMapping("/valid")
    public ResponseEntity<String> validCheck(@RequestParam(name = "id", required = false) String id,
                                             @RequestParam(name = "nickname", required = false) String nickname,
                                             @RequestParam(name = "email", required = false) String email) {


        if (id == null && nickname == null && email == null) {
            return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        Boolean idValid = id != null ? memberRepository.existsById(id) : null;
        Boolean nicknameValid = nickname != null ? memberRepository.existsByNickname(nickname) : null;
        Boolean emailValid = email != null ? memberRepository.existsByEmail(email) : null;

        if (id != null && idValid != null) {
            if (!idValid) {
                return new ResponseEntity<>("사용 가능한 아이디입니다.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("이미 존재하는 아이디입니다.", HttpStatus.CONFLICT);
            }
        }

        if (nickname != null && nicknameValid != null) {
            if (!nicknameValid) {
                return new ResponseEntity<>("사용 가능한 닉네임입니다.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("이미 존재하는 닉네임입니다.", HttpStatus.CONFLICT);
            }
        }
        if (email != null && emailValid != null) {
            if (!emailValid) {
                return new ResponseEntity<>("사용 가능한 이메일입니다.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("이미 존재하는 이메일입니다.", HttpStatus.CONFLICT);
            }
        }

        return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
    }

}
