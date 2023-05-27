package kr.quizthis.QuizThis.dto.jwtDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class UserDto {
    private String username;
    private String email;
    private String password;

}
