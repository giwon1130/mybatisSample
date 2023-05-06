package com.example.mybatis.service.impl;

import com.example.mybatis.mapper.BoardMapper;
import com.example.mybatis.mapper.BoardVO;
import com.example.mybatis.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * [ 템플릿 설명 ]
 * - 해당 파일은 서비스의 비즈니스 로직을 구현하는 곳입니다.
 * - 해당 *ServiceImpl 에서는 @Service 어노테이션을 필수적으로 사용합니다.
 */
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {

    /**
     * '생성자 주입 형태'로 사용합니다.
     * - Autowired 는 권장되지 않기에 생성자 주입 형태로 구성합니다.
     */
    private final SqlSession sqlSession;

    public BoardServiceImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }


    /**
     * Board 리스트 값 조회
     *
     * @return List<BoardVO> 반환값
     */
    @Transactional(readOnly = true)
    public List<BoardVO> selectBoardList() {
        BoardMapper bm = sqlSession.getMapper(BoardMapper.class);
        return bm.selectBoardList();
    }

    /**
     * 키 값을 기반으로 Template 리스트 조회
     *
     * @param boardId 조회 키 값
     * @return BoardVO 반환 값
     */
    @Transactional(readOnly = true)
    public BoardVO selectBoardById(Integer boardId) {
        BoardMapper bm = sqlSession.getMapper(BoardMapper.class);
        return bm.selectBoardById(boardId);
    }

    /**
     * Template INSERT
     *
     * @param boardVO 저장 할 값
     * @return BoardVO 결과값 반환
     */
    @Transactional
    public int insertBoard(BoardVO boardVO) {
        BoardMapper bm = sqlSession.getMapper(BoardMapper.class);
        return bm.insertBoard(boardVO);
    }

    /**
     * Template Update
     *
     * @param boardVO Update Value
     * @return BoardVO 결과값 반환
     */
    @Transactional
    public int updateBoard(BoardVO boardVO) {
        BoardMapper bm = sqlSession.getMapper(BoardMapper.class);
        return bm.updateBoard(boardVO);
    }

    /**
     * Template Delete
     *
     * @param boardId 삭제 아이디
     * @return TemplateVO 결과값 반환
     */
    @Transactional
    public int deleteBoardById(Integer boardId) {
        BoardMapper bm = sqlSession.getMapper(BoardMapper.class);
        return bm.deleteBoardById(boardId);
    }
}