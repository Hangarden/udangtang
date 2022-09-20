package com.example.udangtang.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") //컨트롤러가 먼저 실행되서 index가 아닌 home.html실행
    public String home() {
        return "home";  //home.html로 연결
    }
}
