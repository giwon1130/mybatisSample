package com.example.mybatis.controller;

import com.example.mybatis.mapper.Board;
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


    //////////////////// MVC ////////////////////
    /**
     * [VIEW] Thymeleaf 화면 만을 출력하는 함수
     *
     * @param model 전송 할 데이터
     * @return 페이지
     */
    @GetMapping("main")
    public String selectBoardView(Model model) {
        model.addAttribute("title", "템플릿 화면");
        List<Board> boardList = boardService.selectBoardList();
        model.addAttribute("boardList", boardList);
        return "index";
    }

    @GetMapping("{boardId}")
    public String selectTemplateById(Model model, @PathVariable Integer boardId) {
        Board result = boardService.selectBoardById(boardId);
        model.addAttribute("boardInfo", result);
        return "boardView";
    }

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("title", "템플릿 화면");
        return "boardPage";
    }

    //////////////////// AJAX ////////////////////

    /**
     * [API] 템플릿 리스트 출력 함수
     *
     * @return ApiResponseWrapper<List < BoardVO>> : 응답 결과 및 응답 코드 반환
     */
    @GetMapping("boardList")
    public ResponseEntity<List<Board>> selectBoardList() {
        List<Board> resultList = boardService.selectBoardList();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    /**
     * [API] 템플릿 아이디 별 조회
     *
     * @param boardId 템플릿 생성 데이터
     * @return ApiResponseWrapper<BoardVO> : 응답 결과 및 응답 코드 반환
     */
//    @ResponseBody
//    @GetMapping("{boardId}")
//    public ResponseEntity<Board> selectTemplateById(@PathVariable Integer boardId) {
//        Board result = boardService.selectBoardById(boardId);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    /**
     * [API] 템플릿 생성 함수
     *
     * @param board 템플릿 생성 데이터
     * @return ApiResponseWrapper<BoardVO> : 응답 결과 및 응답 코드 반환
     */
    @ResponseBody
    @PostMapping("")
    public ResponseEntity<Integer> insertBoard(@RequestBody Board board) {
        Integer result = boardService.insertBoard(board);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * [API] 템플릿 수정 함수
     *
     * @param board 템플릿 생성 데이터
     * @return ApiResponseWrapper<Board> : 응답 결과 및 응답 코드 반환
     */
    @ResponseBody
    @PatchMapping("")
    public ResponseEntity<Integer> updateBoard(@RequestBody Board board) {
        Integer result = boardService.updateBoard(board);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * [API] 템플릿 삭제 함수
     *
     * @param boardId 템플릿 생성 데이터
     * @return ApiResponseWrapper<BoardVO> : 응답 결과 및 응답 코드 반환
     */
    @ResponseBody
    @DeleteMapping("{boardId}")
    public ResponseEntity<Integer> deleteBoard(@PathVariable Integer boardId) {
        Integer result = boardService.deleteBoardById(boardId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
