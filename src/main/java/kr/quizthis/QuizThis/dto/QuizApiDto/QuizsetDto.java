package kr.quizthis.QuizThis.dto.QuizApiDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizsetDto {
    private String title;
    private String description;
}
