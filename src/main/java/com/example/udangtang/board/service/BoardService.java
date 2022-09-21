package com.example.udangtang.board.service;

import com.example.udangtang.board.entity.Board;
import com.example.udangtang.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public void write(Board board, MultipartFile file) throws Exception { //null인경우?
//html과 controller의 매개변수 이름을 일치 시켜야 한다.
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
        //String projectPath system.getProperty(”user.dir”)하면 현재 프로젝트 경로를 담아주게 된다.
        //\\src\\main\\resources\\static\\files"로 경로 잡아주고
        UUID uuid = UUID.randomUUID();
// 랜덤 식별자 생성 후
        if( file != null){
            String fileName = uuid + "_" + file.getOriginalFilename();
// 랜덤생성식별자_원래파일이름으로 파일 이름 설정
            File saveFile = new File(projectPath, fileName);
//projectPath에 위 fileName으로 파일 저장
            file.transferTo(saveFile);

            board.setFilename(fileName);
            board.setFilepath("/files/" + fileName);
        }

        //파일 저장
        boardRepository.save(board);
    }

    public void write(Board board){

    }

    public List<Board> boardList() {

        return boardRepository.findAll();
    }

    public Board boardview(Integer id) {

        return boardRepository.findById(id).get();
    }

    public void boardDelete(Integer id) {

        boardRepository.deleteById(id);
    }

}
