package kr.quizthis.QuizThis.controller;

import kr.quizthis.QuizThis.dto.QuizApiDto.QuizsetDto;
import kr.quizthis.QuizThis.dto.QuizApiDto.QuizsetResponse;
import kr.quizthis.QuizThis.dto.UserDto.UserResponse;
import kr.quizthis.QuizThis.entity.User;
import kr.quizthis.QuizThis.repository.UserRepository;
import kr.quizthis.QuizThis.service.QuizService;
import kr.quizthis.QuizThis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;


    @GetMapping("/user")
    public ResponseEntity<UserResponse> userData(
    ) {
        return ResponseEntity.ok(userService.getUserData());
    }

}
