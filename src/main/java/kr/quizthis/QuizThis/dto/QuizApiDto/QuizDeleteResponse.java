package kr.quizthis.QuizThis.dto.QuizApiDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizDeleteResponse {
    private String status;
    private String error;
}
