package kr.quizthis.QuizThis.service;

import kr.quizthis.QuizThis.dto.UserDto.UserResponse;
import kr.quizthis.QuizThis.entity.Quizset;
import kr.quizthis.QuizThis.entity.User;
import kr.quizthis.QuizThis.repository.QuizSetRepository;
import kr.quizthis.QuizThis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizSetRepository quizSetRepository;

    public UserResponse getUserData(){
        // 해당 사용자의 데이터
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        List<Quizset> quizsetList = quizSetRepository.findByUserId(user.getId());
        return UserResponse.builder()
                .username(user.getUsername())
                .userid(user.getId())
                .email(user.getEmail())
                .quizsets(quizsetList.size())
                .build();

    }

}
