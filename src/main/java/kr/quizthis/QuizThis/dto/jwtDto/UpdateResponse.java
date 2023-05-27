package kr.quizthis.QuizThis.dto.jwtDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateResponse {
    private String username;
    private String email;
    private String password;
    private String error;

}
