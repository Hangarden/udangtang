package com.example.udangtang.board.controller;

import com.example.udangtang.board.entity.Board;
import com.example.udangtang.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class BoardController {


    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteForm() {

        return "boardwrite";
    }


    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception{

        boardService.write(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model) {

        model.addAttribute("list", boardService.boardList()); //list를 반환하며 그 리스트의 이름은 list

        return "boardList";
    }


    @GetMapping("/board/view")
    public String boardView(Model model, Integer id) {

        model.addAttribute("board", boardService.boardview(id));

        return "boardview";
    }

    @GetMapping("/board/modify/{id}") //{id} 쓰려면 사용한다고 보아도 된다.
    public String boardModify(@PathVariable("id") Integer id, Model model) {//url의 id부분이 인식이 되어 integer 형태로 들어온다는 뜻
//상세페이지 내용이랑 수정할 때 내용이랑 같기 때문이다.
        model.addAttribute("board", boardService.boardview(id));

        return "boardmodify";
    }


    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Model model, Board board, MultipartFile file) throws Exception{

        Board boardTemp = boardService.boardview(id); //기존내용을 가져오고
        boardTemp.setTitle(board.getTitle()); //새로 입력한 내용을
        boardTemp.setContent(board.getContent()); //덮어씌운다.

        boardService.write(boardTemp, file); //저장
        boardService.write(boardTemp);
        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
}


    @GetMapping("/board/delete")
    public String boardDelete(Integer id) {

        boardService.boardDelete(id);

        return "redirect:/board/list";
    }
}

//url에 파라미터를 넘기는 2가지 방법
//쿼리 스트링 패스 베리어블
