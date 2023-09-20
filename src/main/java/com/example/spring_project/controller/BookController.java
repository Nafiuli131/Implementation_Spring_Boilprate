package com.example.spring_project.controller;

import com.example.spring_project.dto.RequestBookDto;
import com.example.spring_project.security.helper.JwtTokenUtil;
import com.example.spring_project.service.BookService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/book")
public class BookController{
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<?> saveBook(HttpServletRequest request, @RequestBody RequestBookDto bookDto){
        Claims user = null;
        String jwtToken = jwtTokenUtil.extractTokenFromRequest(request);
        if (jwtToken != null) {
             user = jwtTokenUtil.extractUserId(jwtToken);
        }
            return new ResponseEntity<>(bookService.save(user.get("userId"),bookDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findAllBook(HttpServletRequest request){
        Claims user = null;
        String jwtToken = jwtTokenUtil.extractTokenFromRequest(request);
        if (jwtToken != null) {
            user = jwtTokenUtil.extractUserId(jwtToken);
        }
        return new ResponseEntity<>(bookService.findAllBook(user.get("userId")), HttpStatus.OK);
    }

}
