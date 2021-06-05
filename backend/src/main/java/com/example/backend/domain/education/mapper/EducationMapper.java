package com.example.backend.domain.education.mapper;

import com.example.backend.domain.education.domain.Education;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EducationMapper {

    @Insert("INSERT INTO education(user_idx, title, agency, start_date, end_date, content, edit, score) VALUES (#{education.user_idx}, #{education.title}, #{education.agency}," +
            "#{education.start_date}, #{education.end_date}, #{education.content}, NOW(), #{education.score})")
    @Options(useGeneratedKeys = true, keyProperty = "education.idx")
    int save(@Param("education") Education education);

    @Select("SELECT * FROM education WHERE user_idx = #{user_idx} ORDER BY start_date")
    List<Education> findByUserIdx(@Param("user_idx") int user_idx);

    @Select("SELECT * FROM education WHERE idx = #{idx}")
    Optional<Education> findByIdx(@Param("idx") int idx);

    @Update("UPDATE education SET title = #{education.title}, agency = #{education.agency}, start_date = #{education.start_date}, end_date = #{education.end_date}, " +
            "content = #{education.content}, edit = NOW(), score = #{education.score} WHERE idx = #{education.idx} AND user_idx = #{education.user_idx}")
    void update(@Param("education") Education education);

    @Delete("DELETE FROM education WHERE idx = #{idx} and user_idx = #{user_idx} ")
    void delete(@Param("idx") int idx , @Param("user_idx") int user_idx);

    @Select("SELECT COUNT(*) FROM education  WHERE user_idx = #{user_idx}")
    int educationCount(@Param("user_idx") int user_idx);



}
