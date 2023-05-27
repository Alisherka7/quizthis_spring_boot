package kr.quizthis.QuizThis.dto.QuizApiDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private String question;
    private String answer;
    private Integer quizsetid;
}
