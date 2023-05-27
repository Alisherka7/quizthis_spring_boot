package kr.quizthis.QuizThis.controller;


import kr.quizthis.QuizThis.dto.QuizApiDto.QuizsetDeleteResponse;
import kr.quizthis.QuizThis.dto.QuizApiDto.QuizsetDto;
import kr.quizthis.QuizThis.dto.QuizApiDto.QuizsetResponse;
import kr.quizthis.QuizThis.dto.QuizApiDto.QuizsetUpdateDto;
import kr.quizthis.QuizThis.dto.jwtDto.AuthenticationResponse;
import kr.quizthis.QuizThis.dto.jwtDto.RegistrationDto;
import kr.quizthis.QuizThis.entity.Quizset;
import kr.quizthis.QuizThis.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quizset")
@RequiredArgsConstructor
public class QuizSetController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<QuizsetResponse> create(
            @RequestBody QuizsetDto request
    ) {
        return ResponseEntity.ok(quizService.newQuizset(request));
    }

    @GetMapping("/get")
    public ResponseEntity<List<QuizsetResponse>> create() {
        return ResponseEntity.ok(quizService.getQuizsets());
    }

    @PostMapping("/update")
    public ResponseEntity<QuizsetResponse> update(
            @RequestBody QuizsetUpdateDto request
    ) {
        return ResponseEntity.ok(quizService.updateQuizset(request));
    }

    @DeleteMapping("/delete/{quizsetid}")
    public ResponseEntity<QuizsetDeleteResponse> delete(
            @PathVariable Integer quizsetid
    ) {
        return ResponseEntity.ok(quizService.deleteQuizset(quizsetid));
    }
}
