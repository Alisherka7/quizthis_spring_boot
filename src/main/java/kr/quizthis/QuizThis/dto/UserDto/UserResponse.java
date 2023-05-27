package kr.quizthis.QuizThis.dto.UserDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer userid;
    private String username;
    private String email;
    private Integer quizsets;
    private String password;
}
