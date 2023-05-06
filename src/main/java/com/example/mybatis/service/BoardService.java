package com.example.mybatis.service;

import com.example.mybatis.mapper.BoardVO;

import java.util.List;

/**
 * [ 템플릿 설명 ]
 * - 해당 파일은 **ServiceImpl 내에서 구현된 구현체를 사용하기 위한 인터페이스 입니다.
 * - 해당 파일의 기능은 구현체에서 작성한 비즈니스 로직을 Controller 내에서 호출하기 위한 interface 입니다.
 *
 * @author lee
 * @since 2022.09.30
 */
public interface BoardService {
    List<BoardVO> selectBoardList();

    BoardVO selectBoardById(Integer boardId);

    int insertBoard(BoardVO boardVO);

    int updateBoard(BoardVO boardVO);

    int deleteBoardById(Integer boardId);
}