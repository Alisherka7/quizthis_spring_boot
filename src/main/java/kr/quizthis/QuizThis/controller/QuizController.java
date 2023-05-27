package kr.quizthis.QuizThis.controller;

import kr.quizthis.QuizThis.dto.QuizApiDto.*;
import kr.quizthis.QuizThis.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz")
@RequiredArgsConstructor
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<QuizResponse> create(
            @RequestBody QuizDto request
    ) {
        return ResponseEntity.ok(quizService.createQuiz(request));
    }

    @PostMapping("/update")
    public ResponseEntity<QuizResponse> update(
            @RequestBody QuizUpdateDto request
    ) {
        return ResponseEntity.ok(quizService.updateQuiz(request));
    }

    @DeleteMapping("/delete/{quizid}")
    public ResponseEntity<QuizDeleteResponse> delete(
            @PathVariable Integer quizid
    ) {
        return ResponseEntity.ok(quizService.deleteQuiz(quizid));
    }

    @GetMapping("/{quizsetId}/get")
    public ResponseEntity<List<QuizResponse>> getQuiz(
            @PathVariable Integer quizsetId
    ) {
        return ResponseEntity.ok(quizService.getQuiz(quizsetId));
    }

    @GetMapping("/quizgame/{quizsetId}")
    public ResponseEntity<List<QuizGameModeResponse>> quizGameMode(
            @PathVariable Integer quizsetId
    ) {
        return ResponseEntity.ok(quizService.quizGame(quizsetId));
    }
}
