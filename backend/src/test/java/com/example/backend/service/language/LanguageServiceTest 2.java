package com.example.backend.service.language;

import com.example.backend.domain.language.exception.CFindSameExamException;
import com.example.backend.global.exception.exception.CNotFoundInfoByIdxException;
import com.example.backend.global.exception.exception.CNotFoundInfoByUserException;
import com.example.backend.global.exception.exception.CNotHaveAccessInfoException;
import com.example.backend.domain.language.domain.Language;
import com.example.backend.domain.language.service.LanguageService;
import com.example.backend.domain.rank.domain.Score;
import com.example.backend.domain.language.mapper.LanguageMapper;
import com.example.backend.domain.rank.mapper.RankMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class LanguageServiceTest {

    @Autowired
    RankMapper rankMapper;

    @Autowired
    LanguageService languageService;

    @Autowired
    LanguageMapper languageMapper;

    @Test
    void save() {

        //given
        Language test = new Language();
        test.setUser_idx(37);
        test.setDivsion("abc");
        test.setExam("TOEIC");
        test.setExam_score("800");
        test.setDate(Date.valueOf("2017-05-01"));
        test.setContent("abc");

        Score finduser = rankMapper.FindScore(test.getUser_idx());

        //when
        languageService.save(test);

        //then
        assertThat(test.getExam()).isEqualTo(languageMapper.findByIdx(test.getIdx()).get().getExam());

        Score checkuser = rankMapper.FindScore(test.getUser_idx());
        assertThat(checkuser.getLanguage_score()).isEqualTo(finduser.getLanguage_score() + languageMapper.findByIdx(test.getIdx()).get().getScore());
        assertThat(checkuser.getScore()).isEqualTo(finduser.getScore() + languageMapper.findByIdx(test.getIdx()).get().getScore());

    }

    @Test
    void findByUserIdx() {


        //given
        Language test = new Language();
        test.setUser_idx(37);
        test.setDivsion("abc");
        test.setExam("TOEIC");
        test.setExam_score("800");
        test.setDate(Date.valueOf("2017-05-01"));
        test.setContent("abc");

        Score finduser = rankMapper.FindScore(test.getUser_idx());

        //when
        languageService.save(test);

        assertThat(languageService.findByUserIdx(test.getUser_idx()).size()).isEqualTo(1);


    }

    @Test
    void modify() {
        //given
        Language test = new Language();
        test.setUser_idx(37);
        test.setDivsion("abc");
        test.setExam("TOEIC");
        test.setExam_score("800");
        test.setDate(Date.valueOf("2017-05-01"));
        test.setContent("abc");

        Language change = new Language();
        change.setUser_idx(37);
        change.setDivsion("abc");
        change.setExam("TOEIC");
        change.setExam_score("24");
        change.setDate(Date.valueOf("2017-07-01"));
        change.setContent("abcde");

        Score finduser = rankMapper.FindScore(test.getUser_idx());

        //when
        languageService.save(test);
        change.setIdx(test.getIdx());
        languageService.modify(change);

        //then
        assertThat(languageMapper.findByIdx(test.getIdx()).get().getExam()).isEqualTo("TOEIC");

        Score checkuser = rankMapper.FindScore(test.getUser_idx());
        assertThat(checkuser.getLanguage_score()).isEqualTo(finduser.getLanguage_score() + languageMapper.findByIdx(test.getIdx()).get().getScore());
        assertThat(checkuser.getScore()).isEqualTo(finduser.getScore() + languageMapper.findByIdx(test.getIdx()).get().getScore());


    }

    @Test
    void delete() {

        //given
        Language test = new Language();
        test.setUser_idx(37);
        test.setDivsion("abc");
        test.setExam("TOEIC");
        test.setExam_score("800");
        test.setDate(Date.valueOf("2017-05-01"));
        test.setContent("abc");

        Language change = new Language();
        change.setUser_idx(37);
        change.setDivsion("abc");
        change.setExam("TOEFL Reading");
        change.setExam_score("24");
        change.setDate(Date.valueOf("2017-07-01"));
        change.setContent("abcde");

        //when
        languageService.save(test);
        Score finduser = rankMapper.FindScore(test.getUser_idx());
        languageService.save(change);
        languageService.delete(change.getIdx(), change.getUser_idx());

        //then
        assertThat(languageService.findByUserIdx(test.getUser_idx()).size()).isEqualTo(1);

        Score checkuser = rankMapper.FindScore(test.getUser_idx());
        assertThat(checkuser.getLanguage_score()).isEqualTo(finduser.getLanguage_score());
        assertThat(checkuser.getScore()).isEqualTo(finduser.getScore());



    }

    @Test
    void changetoNumber() {

        Language test = new Language();
        test.setUser_idx(37);
        test.setDivsion("abc");
        test.setExam("TOEIC");
        test.setExam_score("800");
        test.setDate(Date.valueOf("2017-05-01"));
        test.setContent("abc");

        Language test3 = new Language();
        test3.setUser_idx(37);
        test3.setDivsion("abc");
        test3.setExam("TOEFL Reading");
        test3.setExam_score("24");
        test3.setDate(Date.valueOf("2017-05-01"));
        test3.setContent("abc");

        Language test5 = new Language();
        test5.setUser_idx(37);
        test5.setDivsion("abc");
        test5.setExam("TOEFL Listening");
        test5.setExam_score("37");
        test5.setDate(Date.valueOf("2017-05-01"));
        test5.setContent("abc");


        Language test7 = new Language();
        test7.setUser_idx(37);
        test7.setDivsion("abc");
        test7.setExam("TOEFL Speaking");
        test7.setExam_score("22");
        test7.setDate(Date.valueOf("2017-05-01"));
        test7.setContent("abc");

        Language test9 = new Language();
        test9.setUser_idx(37);
        test9.setDivsion("abc");
        test9.setExam("TOEFL Writing");
        test9.setExam_score("15");
        test9.setDate(Date.valueOf("2017-05-01"));
        test9.setContent("abc");

        Language test10 = new Language();
        test10.setUser_idx(37);
        test10.setDivsion("abc");
        test10.setExam("TEPS");
        test10.setExam_score("550");
        test10.setDate(Date.valueOf("2017-05-01"));
        test10.setContent("abc");

        Language test11 = new Language();
        test11.setUser_idx(37);
        test11.setDivsion("abc");
        test11.setExam("OPIC");
        test11.setExam_score("IM1");
        test11.setDate(Date.valueOf("2017-05-01"));
        test11.setContent("abc");

        Language test12 = new Language();
        test12.setUser_idx(37);
        test12.setDivsion("abc");
        test12.setExam("JLPT");
        test12.setExam_score("N2");
        test12.setDate(Date.valueOf("2017-05-01"));
        test12.setContent("abc");

        Language test13 = new Language();
        test13.setUser_idx(37);
        test13.setDivsion("abc");
        test13.setExam("HSK");
        test13.setExam_score("4급");
        test13.setDate(Date.valueOf("2017-05-01"));
        test13.setContent("abc");



        //when
        languageService.save(test);
        languageService.save(test3);
        languageService.save(test5);
        languageService.save(test7);
        languageService.save(test9);
        languageService.save(test10);
        languageService.save(test11);
        languageService.save(test12);
        languageService.save(test13);

        //then
        assertThat(languageMapper.findByIdx(test.getIdx()).get().getScore()).isEqualTo(80);
        assertThat(languageMapper.findByIdx(test3.getIdx()).get().getScore()).isEqualTo(100);
        assertThat(languageMapper.findByIdx(test5.getIdx()).get().getScore()).isEqualTo(100);
        assertThat(languageMapper.findByIdx(test7.getIdx()).get().getScore()).isEqualTo(80);
        assertThat(languageMapper.findByIdx(test9.getIdx()).get().getScore()).isEqualTo(60);
        assertThat(languageMapper.findByIdx(test10.getIdx()).get().getScore()).isEqualTo(55);
        assertThat(languageMapper.findByIdx(test11.getIdx()).get().getScore()).isEqualTo(60);
        assertThat(languageMapper.findByIdx(test12.getIdx()).get().getScore()).isEqualTo(80);
        assertThat(languageMapper.findByIdx(test13.getIdx()).get().getScore()).isEqualTo(60);

        Score checkuser = rankMapper.FindScore(test.getUser_idx());
        assertThat(checkuser.getScore()).isEqualTo(675);

    }

    @Test
    void checkLanguageUserIdx() {

        //given
        int user_idx = 37;


        //when
        CNotFoundInfoByUserException e = assertThrows(CNotFoundInfoByUserException.class, () -> languageService.findByUserIdx(user_idx));


        //then
        assertThat(e.getMessage()).isEqualTo("해당 회원의 어학 정보가 없습니다.");
    }

    @Test
    void checkAll() {

        Language test = new Language();
        test.setUser_idx(37);
        test.setDivsion("abc");
        test.setExam("TOEIC");
        test.setExam_score("800");
        test.setDate(Date.valueOf("2017-05-01"));
        test.setContent("abc");

        Language test2 = new Language();
        test2.setUser_idx(37);
        test2.setDivsion("abc");
        test2.setExam("TOEIC");
        test2.setExam_score("800");
        test2.setDate(Date.valueOf("2017-05-01"));
        test2.setContent("abc");

        //when
        languageService.save(test);
        CNotHaveAccessInfoException e = assertThrows(CNotHaveAccessInfoException.class , () -> languageService.delete(test.getIdx(),14));
        CNotFoundInfoByIdxException ex = assertThrows(CNotFoundInfoByIdxException.class , () -> languageService.delete(222,37));
        CFindSameExamException ey = assertThrows(CFindSameExamException.class, () -> languageService.save(test2));


        //then
        assertThat(e.getMessage()).isEqualTo("해당 회원의 어학 번호가 아닙니다.");
        assertThat(ex.getMessage()).isEqualTo("해당 어학 번호의 정보가 없습니다.");




    }
}