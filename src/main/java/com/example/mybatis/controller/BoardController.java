package com.example.mybatis.controller;

import com.example.mybatis.mapper.BoardVO;
import com.example.mybatis.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * [ 템플릿 설명 ]
 * - 해당 파일은 Restful API 형태로 URL 관리하는 파일입니다
 * - 뷰 자체만을 리턴해주거나 API 호출에 대한 처리를 해줍니다.
 * [ Annotation 설명 ]
 * - @Slf4j : 로그를 위해 사용하는 Annotation
 * - @RestController: Restful API 구조에서 JSON 타입으로 데이터를 반환 받기 위해 사용하는 Annotation
 * - @Controller: Spring MVC 패턴을 사용하기 위한 Annotation
 * - @RequestMapper: Controller URL Default
 *
 * @author lee
 * @since 2022.09.30
 */
@Slf4j
@RequestMapping("/board")
@Controller
public class BoardController {

    // 필드 주입이 아닌 생성자 주입형태로 사용합니다. '생성자 주입 형태'로 사용합니다.
    private final BoardService boardService;

    public BoardController(BoardService bs) {
        this.boardService = bs;
    }


    /**
     * [VIEW] Thymeleaf 화면 만을 출력하는 함수
     *
     * @param model 전송 할 데이터
     * @return 페이지
     */
    @GetMapping("main")
    public String selectBoardView(Model model) {
        model.addAttribute("title", "템플릿 화면");
        return "index";
    }

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("title", "템플릿 화면");
        return "boardPage";
    }

    /**
     * [API] 템플릿 리스트 출력 함수
     *
     * @return ApiResponseWrapper<List < BoardVO>> : 응답 결과 및 응답 코드 반환
     */
    @GetMapping("boardList")
    public ResponseEntity<List<BoardVO>> selectBoardList() {
        List<BoardVO> resultList = boardService.selectBoardList();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    /**
     * [API] 템플릿 아이디 별 조회
     *
     * @param boardVO 템플릿 생성 데이터
     * @return ApiResponseWrapper<BoardVO> : 응답 결과 및 응답 코드 반환
     */

    @GetMapping("selectBoardById")
    public ResponseEntity<BoardVO> selectTemplateById(@RequestBody BoardVO boardVO) {
        BoardVO result = boardService.selectBoardById(boardVO.getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * [API] 템플릿 생성 함수
     *
     * @param boardVO 템플릿 생성 데이터
     * @return ApiResponseWrapper<BoardVO> : 응답 결과 및 응답 코드 반환
     */
    @PutMapping("insertBoard")
    public ResponseEntity<Integer> insertBoard(@RequestBody BoardVO boardVO) {
        Integer result = boardService.insertBoard(boardVO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * [API] 템플릿 수정 함수
     *
     * @param boardVO 템플릿 생성 데이터
     * @return ApiResponseWrapper<BoardVO> : 응답 결과 및 응답 코드 반환
     */
    @PatchMapping("updateBoard")
    public ResponseEntity<Integer> updateBoard(@RequestBody BoardVO boardVO) {
        Integer result = boardService.updateBoard(boardVO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * [API] 템플릿 삭제 함수
     *
     * @param boardVO 템플릿 생성 데이터
     * @return ApiResponseWrapper<BoardVO> : 응답 결과 및 응답 코드 반환
     */
    @DeleteMapping("deleteBoard")
    public ResponseEntity<Integer> deleteBoard(@RequestBody BoardVO boardVO) {
        Integer result = boardService.deleteBoardById(boardVO.getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}