package com.example.backend.domain.user.controller;

import com.example.backend.global.exception.exception.CUserLoginFailException;
import com.example.backend.global.config.security.JwtTokenProvider;
import com.example.backend.domain.user.domain.User;
import com.example.backend.global.response.*;
import com.example.backend.global.response.ResponseService;
import com.example.backend.domain.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@Api(tags = {"1. Sign"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignController {


    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());


    //로그인
    @ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 입력하면 회원 정보를 반환해준다.")
    @PostMapping("/signin")
    public SingleResult<String> signin(@ApiParam(value = "회원 아이디(id) ", required = true) @RequestParam String id,
                                       @ApiParam(value = "회원 비밀번호(pass) ", required = true) @RequestParam String pass) {

        logger.info("로그인");
        User user = userService.findById(id);
        if (!passwordEncoder.matches(pass, user.getPassword()))
            throw new CUserLoginFailException();


        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getIdx()), Arrays.asList(user.getRole())));

    }

    //회원가입
    @ApiOperation(value = "회원 가입", notes = "회원정보를 입력하여 가입한다.")
    @PostMapping("/signup")
    public CommonResult signup(@ApiParam(value = "회원 아이디", required = true) @RequestParam String id,
                               @ApiParam(value = "회원 비밀번호", required = true) @RequestParam String pass,
                               @ApiParam(value = "회원 이름", required = true) @RequestParam String name,
                               @ApiParam(value = "회원 학년", required = true) @RequestParam int grade,
                               @ApiParam(value = "회원 성별", required = true) @RequestParam String sex,
                               @ApiParam(value = "회원 이메일", required = true) @RequestParam String mail,
                               @ApiParam(value = "회원 휴대폰 번호", required = true) @RequestParam String phone,
                               @ApiParam(value = "회원 목표직종", required = true) @RequestParam String objective,
                               @ApiParam(value = "회원 목표기업", required = true) @RequestParam String enterprise) {

        userService.signup(User.builder()
                .id(id)
                .pass(passwordEncoder.encode(pass))
                .name(name)
                .grade(grade)
                .sex(sex)
                .mail(mail)
                .phone(phone)
                .objective(objective)
                .enterprise(enterprise)
                .role("ROLE_USER")
                .build());

        return responseService.getSuccessResultMsg("회원가입이 성공하였습니다.");

    }

    //회원 아이디 중복 체크
    @ApiOperation(value = "회원 아이디 중복 체크", notes = "회원 아이디가 중복인지 체크한다.")
    @GetMapping("/signup/check/{id}")
    public CommonResult registerCheck(@ApiParam(value = "회원 아이디(id)", required = true) @PathVariable("id") String id) {
        userService.validateDuplicateUser(id);
        return responseService.getSuccessResultMsg("해당 아이디 사용이 가능합니다.");

    }


}
