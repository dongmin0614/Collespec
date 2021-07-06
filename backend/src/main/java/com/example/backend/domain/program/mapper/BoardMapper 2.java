package com.example.backend.domain.program.mapper;

import com.example.backend.domain.program.domain.Board;
import com.example.backend.domain.program.domain.BoardList;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {

    @Insert("INSERT INTO board(program_idx, user_id, title, content, edit) VALUES (#{board.program_idx}, #{board.user_id}, #{board.title}, #{board.content}," +
            "NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "board.idx")
    void save(@Param("board") Board board);

    @Select("SELECT idx,user_id, title, edit FROM board WHERE program_idx = #{program_idx} ORDER BY idx")
    List<BoardList> findByProgram_idx_NoComment(@Param("program_idx") int program_idx);


    @Update("UPDATE board SET title = #{board.title}, content = #{board.content}, " +
            "edit = NOW() WHERE idx = #{board.idx} AND user_id = #{board.user_id}")
    void update(@Param("board") Board board);


    @Delete("DELETE FROM board WHERE idx = #{idx} and user_id = #{user_id} ")
    void delete(@Param("idx") int idx , @Param("user_id") String user_id);


    @Select("SELECT * FROM board WHERE idx = #{idx}")
    @Result(property = "idx", column = "idx", id = true)
    @Result(property = "commentList", column = "idx", many = @Many(select = "com.example.backend.domain.program.mapper.CommentMapper.findByBoard_idx"))
    Optional<Board> findByIdx(@Param("idx") int idx);



}
